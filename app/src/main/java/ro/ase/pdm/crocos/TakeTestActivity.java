package ro.ase.pdm.crocos;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class TakeTestActivity extends AppCompatActivity {

    private TextView tvCountDown;
    private CountDownTimer timer;
    private long timeLeftInMilliSeconds = 900000;
    private boolean timeIsRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_test);

        tvCountDown = findViewById(R.id.tvCountDown);

        startTimer();

        tvCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });
    }

    private void startTimer() {
        timer = new CountDownTimer(timeLeftInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliSeconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        timeIsRunning = true;

    }

    private void updateTimer() {
        int minutes = (int) (timeLeftInMilliSeconds / 1000) / 60;
        int seconds = (int) (timeLeftInMilliSeconds / 1000) % 60;

        String timeLeftText = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        tvCountDown.setText(timeLeftText);
    }
}
