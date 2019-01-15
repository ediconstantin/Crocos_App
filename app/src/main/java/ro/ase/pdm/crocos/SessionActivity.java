package ro.ase.pdm.crocos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import entities.Session;
import utils.Constant;
import utils.HTTPHandler;
import utils.HTTPResponse;
import utils.JSONifier;

public class SessionActivity extends AppCompatActivity implements Constant {

    FloatingActionButton fabNewSession;
    ListView lvSession;
    SessionAdapter adapter;
    List<Session> sessions= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        lvSession = findViewById(R.id.lvSession);

        fabNewSession = findViewById(R.id.fabNewSession);
        fabNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SessionActivity.this,TestActivity.class));
            }
        });

        adapter = new SessionAdapter(this, R.layout.session_item, sessions);
        lvSession.setAdapter(adapter);

        lvSession.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                //Intent intent = new Intent(getBaseContext(), UserSessionActivity.class);
                //Session sendSession = (Session)lvSession.getItemAtPosition(position);
                //intent.putExtra(CURRENT_SESSION, sendSession)
                //startActivity(getBaseContext(), intent);
            }
        });

        initData();
    }

    private void initData(){
        @SuppressLint("StaticFieldLeak")
        HTTPHandler httpHandler = new HTTPHandler(){
            @Override
            protected void onPostExecute(HTTPResponse response){
                if(response.getResult()){
                    sessions.addAll(JSONifier.jsonToSessions(response.getResponse()));
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        httpHandler.execute(GET_METHOD, API_URL + "/session");
    }
}
