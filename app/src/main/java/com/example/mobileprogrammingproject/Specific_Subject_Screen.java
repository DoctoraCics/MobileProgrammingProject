package com.example.mobileprogrammingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileprogrammingproject.model.Subject;
import com.example.mobileprogrammingproject.model.androidDBHandlerSqlLite;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Specific_Subject_Screen extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggling;
    private Toolbar toolbar;

    private NavigationView navigationView;

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

        toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggling =  new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggling);
        toggling.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        readNotes();
        debugToast();

//        try{
//            ArrayList subjectList = db.selectStudentsUnderSubject(SubjectId);
//            ArrayAdapter<Subject> displaySubjectList = new ArrayAdapter<Subject>(
//                    getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, subjectList);
//            displaySubjectList.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item
//            );
//            spinnerHome.setAdapter(displaySubjectList);} catch (Exception e){
//            e.printStackTrace();
//        }

        navigationView = (NavigationView) findViewById(R.id.nav_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        intent = new Intent(Specific_Subject_Screen.this, Home_Screen.class);
                        intent.removeExtra(Home_Screen.SUBJECT_ID);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.about:
                        intent = new Intent(Specific_Subject_Screen.this, About_Screen.class);
                        intent.removeExtra(Home_Screen.SUBJECT_ID);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.subjects:
                        intent = new Intent(Specific_Subject_Screen.this, Subject_Screen.class);
                        intent.removeExtra(Home_Screen.SUBJECT_ID);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.exit:
                        System.exit(0);
                }
                return true;
            }
        });
    }
    public void enrollStudentScreen(View view){
        Intent intent = new Intent(this, Enroll_Student_Screen.class);
        startActivity(intent);
    }

    public void saveNotes(View view){
        try{
            Toast.makeText(getApplicationContext(),"Notes Saved!", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("NotePreferences",MODE_PRIVATE);

            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            int subId = intent.getIntExtra(Home_Screen.SUBJECT_ID, 0);
            myEdit.putString(String.valueOf(subId), enterNotes.getText().toString());
            myEdit.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Notes Failed to Save", Toast.LENGTH_SHORT).show();
        }
    }

    public void readNotes(){
        SharedPreferences sharedPreferences = getSharedPreferences("NotePreferences", MODE_APPEND);
        int subId = intent.getIntExtra(Home_Screen.SUBJECT_ID, 0);
        String s1 = sharedPreferences.getString(String.valueOf(subId), "");
        enterNotes.setText(s1);
    }

    public void deleteNotes(View view){
        try {
            Toast.makeText(getApplicationContext(),"Notes Cleared!", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("NotePreferences",MODE_PRIVATE);

            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            int subId = intent.getIntExtra(Home_Screen.SUBJECT_ID, 0);
            myEdit.remove(String.valueOf(subId));
            enterNotes.setText("");
            myEdit.commit();
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Deletion Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateStudentScreen(View view){
        Intent intent = new Intent(this, Update_Student_Screen.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(toggling.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goBacktoHome(){
        Intent intent = new Intent(this, Home_Screen.class);
        startActivity(intent);
        finish();
    }

    public void debugToast(){
        Toast.makeText(getApplicationContext(),"Subject Id: " + intent.getIntExtra(Home_Screen.SUBJECT_ID, 0), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed(){

    }
}