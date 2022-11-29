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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobileprogrammingproject.model.Subject;
import com.example.mobileprogrammingproject.model.androidDBHandlerSqlLite;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Home_Screen extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggling;
    private Toolbar toolbar;
    private Spinner spinnerHome;

    private NavigationView navigationView;
    public Intent intent;

    private androidDBHandlerSqlLite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        spinnerHome = (Spinner) findViewById(R.id.spinnerHome);
        db = new androidDBHandlerSqlLite(this);

        toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggling = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggling);
        toggling.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            ArrayList subjectList = db.selectSubjects();
            ArrayAdapter<Subject> displaySubjectList = new ArrayAdapter<Subject>(
                    getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, subjectList);
            displaySubjectList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
            );
            spinnerHome.setAdapter(displaySubjectList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        navigationView = (NavigationView) findViewById(R.id.nav_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(getApplicationContext(), "You are already at the home screen", Toast.LENGTH_SHORT).show();
                        db.close();
                        removeId();
                        return true;
                    case R.id.subjects:
                        intent = new Intent(Home_Screen.this, Subject_Screen.class);
                        db.close();
                        removeId();
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.about:
                        intent = new Intent(Home_Screen.this, About_Screen.class);
                        db.close();
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
    }

    public void removeId() {
        try{
            intent.removeExtra("SUBJECT_ID");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openSpecificSubjectScreen(View view) {
        Subject selectedSUbject = (Subject) spinnerHome.getSelectedItem();
        if (selectedSUbject != null) {
            int SelectedSubjectId = selectedSUbject.getSubjectId();
            Intent i = new Intent(this, Specific_Subject_Screen.class);
            db.close();
            i.putExtra("SUBJECT_ID", SelectedSubjectId);
            i.putExtra("SUBJECT_NAME",selectedSUbject.getName());
            startActivity(i);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggling.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}