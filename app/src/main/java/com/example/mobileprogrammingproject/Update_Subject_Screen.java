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

public class Update_Subject_Screen extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggling;
    private Toolbar toolbar;
    NavigationView navigationView;
    Intent intent;

    private androidDBHandlerSqlLite db;

    private int subjectId;
    private String subjectName;
    private String subjectCode;

    private TextView textViewSubjectName;
    private TextView textViewSubjectCode;
    private Spinner spinner2;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_subject_screen);

        intent = getIntent();

        db = new androidDBHandlerSqlLite(this);

        this.textViewSubjectName = (TextView) findViewById(R.id.subjectname);
        this.textViewSubjectCode = (TextView) findViewById(R.id.subjectCode);
        this.spinner2 = (Spinner) findViewById(R.id.spinner2);

        this.subjectId = intent.getIntExtra("ID", -1);

        if (subjectId == -1) {
            updateSubjectScreen();
        }

        this.subjectName = intent.getStringExtra("SUBJECTNAME");
        this.subjectCode = intent.getStringExtra("SUBJECTCODE");

        textViewSubjectName.setText(subjectName);
        textViewSubjectCode.setText(subjectCode);

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
                        intent = new Intent(Update_Subject_Screen.this, Home_Screen.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.about:
                        intent = new Intent(Update_Subject_Screen.this, About_Screen.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.subjects:
                        intent = new Intent(Update_Subject_Screen.this, Subject_Screen.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.exit:
                        System.exit(0);
                }
                return true;
            }
        });

        button = (Button) findViewById(R.id.updateSubj);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSubjectScreen();
            }
        });
    }

    public void removeExtras(){
        intent.removeExtra("ID");
        intent.removeExtra("SUBJECT_NAME");
        intent.removeExtra("SUBJECT_CODE");
    }

    public void updateSubjectScreen() {

        if (db.updateSubject(subjectId, textViewSubjectName.getText().toString(), textViewSubjectCode.getText().toString(), spinner2.getSelectedItem().toString())) {
            removeExtras();
            Intent intent = new Intent(this, Subject_Screen.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Update Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        removeExtras();
        Intent intent = new Intent(this, Subject_Screen.class);
        intent.putExtra("SUBJECT_NAME", subjectName);
        startActivity(intent);
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggling.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}