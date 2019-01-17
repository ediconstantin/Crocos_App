package ro.ase.pdm.crocos;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import utils.Constant;

public class ProfileActivity extends AppCompatActivity implements Constant {

    TextView tvFirstName, tvLastName, tvGroup, tvSeries;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvFirstName = findViewById(R.id.tvUserFirstName);
        tvLastName = findViewById(R.id.tvUserLastName);
        tvGroup = findViewById(R.id.tvUserGroup);
        tvSeries = findViewById(R.id.tvUserSeries);



        sharedPreferences = getSharedPreferences(Constant.NAIRU_PREFERENCES, MODE_PRIVATE);


        tvFirstName.setText(sharedPreferences.getString(FIRSTNAME,"user"));
        tvLastName.setText(sharedPreferences.getString(LASTNAME,"user"));
        tvGroup.setText(sharedPreferences.getString(GROUP,"1000"));
        tvSeries.setText(sharedPreferences.getString(SERIES,"A"));


    }
}
