package com.example.mobileprogrammingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileprogrammingproject.model.androidDBHandlerSqlLite;
import com.google.android.material.navigation.NavigationView;

public class Update_Student_Screen extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggling;
    private Toolbar toolbar;
    NavigationView navigationView;
    Intent intent;

    private int Subject_Id;
    private int Student_Number;

    private androidDBHandlerSqlLite db;
    private String subjectName;

    private TextView enterName;
    private TextView grade;
    private Spinner statusSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student_screen);

        intent = getIntent();

        this.Subject_Id = intent.getIntExtra("SUBJECT_ID", -1);
        this.subjectName = intent.getStringExtra("SUBJECT_NAME");


        this.db = new androidDBHandlerSqlLite(this);
        debugToast();

        toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);

        this.enterName = (TextView) findViewById(R.id.enterName);
        this.grade = (TextView) findViewById(R.id.grade);
        this.statusSelect = (Spinner) findViewById(R.id.spinnerStatus);

        enterName.setText(intent.getStringExtra("STUDENT_NAME"));
        double unformattedGrade = intent.getDoubleExtra("STUDENT_GRADE", 0);
        grade.setText(String.format("%.2f", unformattedGrade));
        this.Student_Number = intent.getIntExtra("STUDENT_NUMBER", -1);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggling = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggling);
        toggling.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        intent = new Intent(Update_Student_Screen.this, Home_Screen.class);
                        startActivity(intent);
                        db.close();
                        finish();
                        return true;
                    case R.id.about:
                        intent = new Intent(Update_Student_Screen.this, About_Screen.class);
                        startActivity(intent);
                        finish();
                        db.close();
                        return true;
                    case R.id.subjects:
                        intent = new Intent(Update_Student_Screen.this, Subject_Screen.class);
                        startActivity(intent);
                        db.close();
                        finish();
                        return true;
                    case R.id.exit:
                        db.close();
                        finish();
                        System.exit(0);
                }
                return true;
            }
        });

    }

    public void onBackPressed() {
        openSpecificSubjectScreen();
    }

    public void updateStudent(View view) {
        if (db.updateStudent(Subject_Id, Student_Number, enterName.getText().toString(),
                Float.parseFloat(grade.getText().toString()),
                statusSelect.getSelectedItem().toString())){
            openSpecificSubjectScreen();
        }else {
            Toast.makeText(getApplicationContext(), "Update Failed!", Toast.LENGTH_SHORT).show();
        }

    }

    public void openSpecificSubjectScreen(View view) {
        Intent intent = new Intent(this, Specific_Subject_Screen.class);
        intent.putExtra("SUBJECT_ID", Subject_Id);
        intent.putExtra("SUBJECT_NAME", subjectName);
        db.close();
        startActivity(intent);
        finish();
    }

    public void openSpecificSubjectScreen() {
        Intent intent = new Intent(this, Specific_Subject_Screen.class);
        intent.putExtra("SUBJECT_ID", Subject_Id);
        intent.putExtra("SUBJECT_NAME", subjectName);
        db.close();
        startActivity(intent);
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggling.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void debugToast() {
        Toast.makeText(getApplicationContext(), "Subject Id: " + Subject_Id, Toast.LENGTH_SHORT).show();
    }

}