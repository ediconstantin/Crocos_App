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


public class TeacherActivity extends AppCompatActivity {

    private BottomNavigationView mainBottomNav;
    private Button nSignOut;
    private QuizFragment quizFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);


        mainBottomNav = (BottomNavigationView)findViewById(R.id.bottomNavBar);
        mainBottomNav.setSelectedItemId(R.id.itmQuizz);

        quizFragment = new QuizFragment();

        replaceFragment(quizFragment);

        mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.itmQuizz:
                        replaceFragment(quizFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });


        nSignOut = findViewById(R.id.btnSignOut);

        nSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
                //finish();
                startActivity(new Intent(TeacherActivity.this, AboutActivity.class));
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer, fragment);
        fragmentTransaction.commit();
    }
}
