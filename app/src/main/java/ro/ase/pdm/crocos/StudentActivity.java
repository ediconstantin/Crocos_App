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



public class StudentActivity extends AppCompatActivity {

    private BottomNavigationView mainBottomNav;

    private JoinFragment joinFragment;
    private HistoryFragment historyFragment;
    private ProfileFragment profileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        mainBottomNav = (BottomNavigationView)findViewById(R.id.bottomNavBar);
        mainBottomNav.setSelectedItemId(R.id.itmJoin);


        joinFragment = new JoinFragment();
        historyFragment = new HistoryFragment();
        profileFragment = new ProfileFragment();

        replaceFragment(joinFragment);

        mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.itmJoin:
                        replaceFragment(joinFragment);
                        return true;
                    case R.id.itmProfile:
                        replaceFragment(profileFragment);
                        return true;
                    case R.id.itmHistory:
                        replaceFragment(historyFragment);
                        return true;
                        default:
                            return false;
                }
            }
        });


    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer, fragment);
        fragmentTransaction.commit();

    }



}
