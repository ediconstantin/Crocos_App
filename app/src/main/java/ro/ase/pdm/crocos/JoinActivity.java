package ro.ase.pdm.crocos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import entities.Session;
import utils.Constant;
import utils.HTTPHandler;
import utils.HTTPResponse;
import utils.JSONifier;

public class JoinActivity extends AppCompatActivity implements Constant {

    Session session;
    EditText etToken;
    Button btnJoin;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        etToken = findViewById(R.id.etJoinSession);
        btnJoin = findViewById(R.id.btnJoin);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("StaticFieldLeak")
                HTTPHandler httpHandler = new HTTPHandler(){
                    @Override
                    protected void onPostExecute(HTTPResponse response){
                        if(response.getResult()){

                            session = JSONifier.jsonToSessionWithDuration(response.getResponse());

                            Intent intent = new Intent(JoinActivity.this, sessionBeforeStartActivity.class);
                            intent.putExtra(JOIN_SESSION, session);
                            startActivity(intent);

                        } else {
                            Toast.makeText(JoinActivity.this, "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                token = etToken.getText().toString();
                httpHandler.execute(GET_METHOD, API_URL + "/session/public/" + token);
            }
        });




    }
}
