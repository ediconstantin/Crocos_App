package ro.ase.pdm.crocos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import entities.Session;
import utils.Constant;


public class TeacherActivity extends AppCompatActivity implements Constant {

    private BottomNavigationView mainBottomNav;
    private SharedPreferences sharedPreferences;
    private Button nSignOut;
    private TextView welcomeLabel;
    private FloatingActionButton fabTest;
    private FloatingActionButton fabSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);


        mainBottomNav = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        mainBottomNav.setSelectedItemId(R.id.itmHome);


        sharedPreferences = getSharedPreferences(Constant.NAIRU_PREFERENCES, MODE_PRIVATE);
        fabTest  = findViewById(R.id.fabAddTest);
        fabSession = findViewById(R.id.fabAddSession);
        welcomeLabel = findViewById(R.id.tvWelcomeLabel);

        mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.itmHome:
                        return true;
                    case R.id.itmTest:
                        startActivity(new Intent(TeacherActivity.this, TestActivity.class));
                        return true;
                    case R.id.itmSessions:
                        startActivity(new Intent(TeacherActivity.this, SessionActivity.class));
                        return true;
                    default:
                        return false;
                }
            }
        });

        fabTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherActivity.this,CreateTestActivity.class));
            }
        });

        fabSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherActivity.this, CreateSessionActivity.class));
            }
        });

        String firstName = sharedPreferences.getString(FIRSTNAME, "user");

        welcomeLabel.setText("Welcome " + firstName);








    }
}
