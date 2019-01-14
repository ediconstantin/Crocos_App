package ro.ase.pdm.crocos;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SessionActivity extends AppCompatActivity {

    FloatingActionButton fabNewSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        fabNewSession = findViewById(R.id.fabNewSession);
        fabNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SessionActivity.this,CreateSessionActivity.class));
            }
        });


    }
}
