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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileprogrammingproject.model.androidDBHandlerSqlLite;
import com.google.android.material.navigation.NavigationView;

public class Add_Subject_Screen extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggling;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Intent intent;

    private androidDBHandlerSqlLite db;
    private TextView enterSubj;
    private TextView enterCode;
    private Spinner statusDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject_screen);
        intent = getIntent();

        db = new androidDBHandlerSqlLite(this);

        this.enterSubj = (TextView) findViewById(R.id.enterSubj);
        this.enterCode = (TextView) findViewById(R.id.enterCode);
        this.statusDropdown = (Spinner) findViewById(R.id.statusDropdown);

        toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggling =  new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggling);
        toggling.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        intent = new Intent(Add_Subject_Screen.this, Home_Screen.class);
                        startActivity(intent);
                        db.close();
                        finish();
                        return true;
                    case R.id.about:
                        intent = new Intent(Add_Subject_Screen.this, About_Screen.class);
                        startActivity(intent);
                        db.close();
                        finish();
                        return true;
                    case R.id.subjects:
                        intent = new Intent(Add_Subject_Screen.this, Subject_Screen.class);
                        startActivity(intent);
                        db.close();
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

    public void addSubjectDB(View view){

        String enteredSubj = this.enterSubj.getText().toString();
        String enteredCode = this.enterCode.getText().toString();
        String selectedStatus = this.statusDropdown.getSelectedItem().toString();

        if (db.insertSubject(enteredSubj, enteredCode, selectedStatus)) {
            Toast.makeText(getApplicationContext(),"Success!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Subject_Screen.class);
            db.close();
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(toggling.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}