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

public class Enroll_Student_Screen extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggling;
    private Toolbar toolbar;

    private androidDBHandlerSqlLite db;

    private TextView inputStudentName;
    private TextView inputGrade;
    private Spinner statSpinner;

    NavigationView navigationView;
    Intent intent;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_student_screen);
        intent = getIntent();
        this.db = new androidDBHandlerSqlLite(this);


        this.inputStudentName = (TextView) findViewById(R.id.studentName);
        this.inputGrade = (TextView) findViewById(R.id.enterGrade);
        this.statSpinner = (Spinner) findViewById(R.id.statusSpinner);

        toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);

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
                        intent = new Intent(Enroll_Student_Screen.this, Home_Screen.class);
                        removeId();
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.about:
                        intent = new Intent(Enroll_Student_Screen.this, About_Screen.class);
                        removeId();
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.subjects:
                        intent = new Intent(Enroll_Student_Screen.this, Subject_Screen.class);
                        removeId();
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.exit:
                        System.exit(0);
                        return true;
                }
                return true;
            }
        });

        button = (Button) findViewById(R.id.enrollStudent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSpecificSubjectScreen();
            }
        });

    }

    public void removeId() {
        try {
            intent.removeExtra(Home_Screen.SUBJECT_ID);
        } catch (Exception e) {
        }
    }

    public void openSpecificSubjectScreen() {
        Intent intent = new Intent(this, Specific_Subject_Screen.class);
        startActivity(intent);
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggling.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void enrollStudent(View view) {
        try {
            if (db.enrollStudent(Integer.parseInt(intent.getStringExtra(Home_Screen.SUBJECT_ID)),
                    inputStudentName.getText().toString(),
                    Float.parseFloat(inputGrade.getText().toString()),
                    statSpinner.getSelectedItem().toString())) {
                Toast.makeText(getApplicationContext(), "Enrollment Success!", Toast.LENGTH_SHORT).show();
                openSpecificSubjectScreen();
            }
            else{
                Toast.makeText(getApplicationContext(), "Enrollment Failed!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Enrollment Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}