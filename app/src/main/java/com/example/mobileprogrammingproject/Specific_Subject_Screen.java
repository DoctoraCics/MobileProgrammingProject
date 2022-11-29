package com.example.mobileprogrammingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileprogrammingproject.model.RecyclerInterface;
import com.example.mobileprogrammingproject.model.Student;
import com.example.mobileprogrammingproject.model.androidDBHandlerSqlLite;
import com.example.mobileprogrammingproject.model.recyclerViews.studentUnderSubjectRecycler;
import com.example.mobileprogrammingproject.model.recyclerViews.subjectRecyclerView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Specific_Subject_Screen extends AppCompatActivity implements RecyclerInterface {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggling;
    private Toolbar toolbar;

    private NavigationView navigationView;
    private RecyclerView studentRecycler;

    private ArrayList<Student> studentList;
    private int Subject_Id;

    private String subjectName;

    private Intent intent;
    private androidDBHandlerSqlLite db;
    private SharedPreferences shareDPreferences;
    private SharedPreferences.Editor noteSubjectEdit;

    private TextView enterNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_subject_screen);
        intent = getIntent();
        this.enterNotes = (TextView) findViewById(R.id.enterNotes);
        this.studentRecycler = (RecyclerView) findViewById(R.id.studentRecycler);
        this.db = new androidDBHandlerSqlLite(this);

        this.Subject_Id = intent.getIntExtra("SUBJECT_ID", -1);
        this.studentList = db.selectStudentsUnderSubject(Subject_Id);

        this.subjectName = intent.getStringExtra("SUBJECT_NAME");
        setTitle(subjectName);

        studentUnderSubjectRecycler studentAdaptor = new studentUnderSubjectRecycler(this, studentList, Subject_Id, this);
        studentRecycler.setAdapter(studentAdaptor);
        studentRecycler.setLayoutManager(new LinearLayoutManager(this));

        toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggling = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggling);
        toggling.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        readNotes();
        debugToast();

        navigationView = (NavigationView) findViewById(R.id.nav_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        intent = new Intent(Specific_Subject_Screen.this, Home_Screen.class);
                        removeId();
                        startActivity(intent);
                        db.close();
                        finish();
                        return true;
                    case R.id.about:
                        intent = new Intent(Specific_Subject_Screen.this, About_Screen.class);
                        removeId();
                        startActivity(intent);
                        db.close();
                        finish();
                        return true;
                    case R.id.subjects:
                        intent = new Intent(Specific_Subject_Screen.this, Subject_Screen.class);
                        removeId();
                        startActivity(intent);
                        finish();
                        db.close();
                        return true;
                    case R.id.exit:
                        System.exit(0);
                }
                return true;
            }
        });
    }

    public void removeId() {
        try {
            intent.removeExtra("SUBJECT_ID");
        } catch (Exception e) {
        }
    }

    public void enrollStudentScreen(View view) {
        Intent intent = new Intent(this, Enroll_Student_Screen.class);
        intent.putExtra("SUBJECT_ID",Subject_Id);
        db.close();
        startActivity(intent);
    }

    public void saveNotes(View view) {
        try {
            Toast.makeText(getApplicationContext(), "Notes Saved!", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("NotePreferences", MODE_PRIVATE);

            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString(String.valueOf(Subject_Id), enterNotes.getText().toString());
            myEdit.commit();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Notes Failed to Save", Toast.LENGTH_SHORT).show();
        }
    }

    public void readNotes() {
        SharedPreferences sharedPreferences = getSharedPreferences("NotePreferences", MODE_APPEND);
        String s1 = sharedPreferences.getString(String.valueOf(Subject_Id), "");
        enterNotes.setText(s1);
    }

    public void deleteNotes(View view) {
        try {
            Toast.makeText(getApplicationContext(), "Notes Cleared!", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("NotePreferences", MODE_PRIVATE);

            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.remove(String.valueOf(Subject_Id));
            enterNotes.setText("");
            myEdit.commit();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Deletion Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateStudentScreen(View view) {
        Intent intent = new Intent(this, Update_Student_Screen.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggling.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goBacktoHome() {
        Intent intent = new Intent(this, Home_Screen.class);
        db.close();
        startActivity(intent);
        finish();
    }

    public void debugToast() {
        Toast.makeText(getApplicationContext(), "Subject Id: " + Subject_Id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        goBacktoHome();
    }

    @Override
    public void onUpdateClick(int position) {
        Intent intent = new Intent(this, Update_Student_Screen.class);
        db.close();
        intent.putExtra("SUBJECT_NAME",subjectName);
        intent.putExtra("SUBJECT_ID", Subject_Id);
        intent.putExtra("STUDENT_NUMBER",studentList.get(position).getStudent_Number());
        intent.putExtra("STUDENT_NAME",studentList.get(position).getStudent_Name());
        intent.putExtra("STUDENT_GRADE", studentList.get(position).getStudent_Grade());
        startActivity(intent);
        finish();
    }

    @Override
    public void onDeleteCLick(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Un Enroll")
                .setMessage("Are you sure you want to unenroll this Student?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        unEnrollStudent(position);
                    }
                }).create().show();
    }

    public void unEnrollStudent(int position){
        if(db.unEnrollStudent(studentList.get(position).getStudent_Number())){
            this.studentList = db.selectStudentsUnderSubject(Subject_Id);
            studentUnderSubjectRecycler studentAdaptor = new studentUnderSubjectRecycler(this, studentList, Subject_Id, this);
            studentRecycler.setAdapter(studentAdaptor);
            studentRecycler.setLayoutManager(new LinearLayoutManager(this));
            Toast.makeText(getApplicationContext(), "Student unenrolled", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "unenrollment Failed", Toast.LENGTH_SHORT).show();
        }
    }
}