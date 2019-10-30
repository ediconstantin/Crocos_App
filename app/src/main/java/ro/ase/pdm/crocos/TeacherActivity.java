package ro.ase.pdm.crocos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import entities.Session;


public class TeacherActivity extends AppCompatActivity {

    private BottomNavigationView mainBottomNav;
    private Button nSignOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);


        mainBottomNav = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        mainBottomNav.setSelectedItemId(R.id.itmQuiz);

        mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.itmQuiz:
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

    }
}
