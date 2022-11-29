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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileprogrammingproject.model.RecyclerInterface;
import com.example.mobileprogrammingproject.model.Subject;
import com.example.mobileprogrammingproject.model.androidDBHandlerSqlLite;
import com.example.mobileprogrammingproject.model.recyclerViews.subjectRecyclerView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Subject_Screen extends AppCompatActivity implements RecyclerInterface {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggling;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Intent intent;

    private ArrayList<Subject> retrievedSubj;

    private TextView enterSubj;
    private TextView enterCode;
    private Spinner statusDropdown;
    private androidDBHandlerSqlLite db;
    private RecyclerView subjectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_screen);
        intent = getIntent();

        db = new androidDBHandlerSqlLite(this);
        retrievedSubj = db.selectSubjects();

        this.enterSubj = (TextView) findViewById(R.id.enterSubj);
        this.enterCode = (TextView) findViewById(R.id.enterCode);
        this.statusDropdown = (Spinner) findViewById(R.id.statusDropdown);
        this.subjectView = findViewById(R.id.recyclerSubjects);

        subjectRecyclerView subjectAdapter = new subjectRecyclerView(this, retrievedSubj, this);
        subjectView.setAdapter(subjectAdapter);
        subjectView.setLayoutManager(new LinearLayoutManager(this));

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
                        intent = new Intent(Subject_Screen.this, Home_Screen.class);
                        startActivity(intent);
                        db.close();
                        finish();
                        return true;
                    case R.id.about:
                        intent = new Intent(Subject_Screen.this, About_Screen.class);
                        startActivity(intent);
                        db.close();
                        finish();
                        return true;
                    case R.id.subjects:
                        db.close();
                        Toast.makeText(getApplicationContext(), "You are already at the subject screen", Toast.LENGTH_SHORT).show();
                        finish();
                        return true;
                    case R.id.exit:
                        db.close();
                        System.exit(0);
                }
                return true;
            }
        });
    }

    public void openAddSubjectScreen(View view) {
        Intent intent = new Intent(this, Add_Subject_Screen.class);
        db.close();
        startActivity(intent);
    }

    public void openUpdateSubjectScreen() {
        Intent intent = new Intent(this, Update_Subject_Screen.class);
        db.close();
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggling.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUpdateClick(int position) {

        Intent intent = new Intent(this, Update_Subject_Screen.class);
        intent.putExtra("ID", retrievedSubj.get(position).getSubjectId());
        intent.putExtra("SUBJECTNAME", retrievedSubj.get(position).getName());
        intent.putExtra("SUBJECTCODE", retrievedSubj.get(position).getSubject_Code());
        db.close();
        startActivity(intent);
        finish();
    }

    @Override
    public void onDeleteCLick(int position) {

        new AlertDialog.Builder(this)
                .setTitle("Deletion")
                .setMessage("Are you sure you want to delete this subject?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteSubject(position);
                    }
                }).create().show();
    }

    public void deleteSubject(int position){

        if (db.deleteSubject(retrievedSubj.get(position).getSubjectId())) {
            this.retrievedSubj = db.selectSubjects();
            subjectRecyclerView subjectAdapter = new subjectRecyclerView(this, retrievedSubj, this);
            subjectView.setAdapter(subjectAdapter);
            subjectView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Toast.makeText(getApplicationContext(), "Deletion Failed", Toast.LENGTH_SHORT).show();

        }
    }

}
