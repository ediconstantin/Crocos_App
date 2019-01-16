package ro.ase.pdm.crocos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class sessionBeforeStartActivity extends AppCompatActivity {



    private TextView tvTestValue, tvQuestionsNoValue, tvDurationValue;

    private Button btnStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_before_start);

        tvTestValue = findViewById(R.id.tvTestValue);
        tvQuestionsNoValue = findViewById(R.id.tvQuestionsNoValue);
        tvDurationValue = findViewById(R.id.tvDurationValue);

        btnStart = findViewById(R.id.btnStartTest);

        btnStart.setEnabled(false);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

    }
}
