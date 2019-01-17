package ro.ase.pdm.crocos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class teacherProfileActivity extends AppCompatActivity {

    TextView tvFirstName;
    TextView tvLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        tvFirstName = findViewById(R.id.tvTeacherFirstNameValue);
        tvLastName = findViewById(R.id.tvTeacherLastNameValue);
    }
}
