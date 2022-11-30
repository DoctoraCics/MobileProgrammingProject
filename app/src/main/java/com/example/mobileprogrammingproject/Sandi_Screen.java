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

import com.google.android.material.navigation.NavigationView;

public class Sandi_Screen extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggling;
    private Toolbar toolbar;
    NavigationView navigationView;
    Intent intent;

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandi_screen);

        intent = getIntent();

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
                        intent = new Intent(Sandi_Screen.this, Home_Screen.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.about:
                        intent = new Intent(Sandi_Screen.this, About_Screen.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.subjects:
                        intent = new Intent(Sandi_Screen.this, Subject_Screen.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.exit:
                        finish();
                        System.exit(0);
                }
                return true;
            }
        });

        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeveloperScreen();
            }
        });

    }

    public void openDeveloperScreen(){
        Intent intent = new Intent(this, Developers_Screen.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        openDeveloperScreen();
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