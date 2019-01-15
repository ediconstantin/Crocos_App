package ro.ase.pdm.crocos;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import entities.Session;

public class SessionActivity extends AppCompatActivity {

    FloatingActionButton fabNewSession;
    ListView lvSession;
    SessionAdapter adapter;
    List<Session> sessions= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        lvSession = findViewById(R.id.lvSession);
        initData();

        fabNewSession = findViewById(R.id.fabNewSession);
        fabNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SessionActivity.this,TestActivity.class));
            }
        });


        adapter = new SessionAdapter(this, R.layout.session_item, sessions);
        lvSession.setAdapter(adapter);
    }

    private void initData(){
        Session s1 = new Session();
        s1.setName("First session");
        s1.setStatus(1);

        Session s2 = new Session();
        s2.setName("Second session");
        s2.setStatus(2);

        Session s3 = new Session();
        s3.setName("Third session");
        s3.setStatus(0);

        sessions.add(s1);
        sessions.add(s2);
        sessions.add(s3);

    }
}
