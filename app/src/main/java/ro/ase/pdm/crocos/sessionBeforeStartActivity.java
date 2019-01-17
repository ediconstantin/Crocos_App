package ro.ase.pdm.crocos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import entities.Session;
import entities.TestAnswer;
import entities.TestAnswerList;
import utils.Constant;
import utils.HTTPHandler;
import utils.HTTPResponse;
import utils.JSONifier;

public class sessionBeforeStartActivity extends AppCompatActivity implements Constant{



    private TextView tvTestValue, tvQuestionsNoValue, tvDurationValue;

    private Button btnStart;
    private TestAnswerList testAnswers;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_before_start);

        tvTestValue = findViewById(R.id.tvTestValue);
        tvQuestionsNoValue = findViewById(R.id.tvQuestionsNoValue);
        tvDurationValue = findViewById(R.id.tvDurationValue);

        btnStart = findViewById(R.id.btnStartTest);

        btnStart.setEnabled(false);

        Intent intent = getIntent();

        session = (Session)intent.getSerializableExtra(JOIN_SESSION);

        fetchData();

        tvTestValue.setText(session.getTestName());
        tvDurationValue.setText(String.valueOf(session.getTestDuration()));

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TakeTestActivity.class);

                intent.putExtra(JSON_TEST_TAKEN, testAnswers);
                intent.putExtra(DURATION, session.getTestDuration()*60000);
                startActivity(intent);

                finish();
            }
        });
    }

    private void fetchData(){
        @SuppressLint("StaticFieldLeak")
        HTTPHandler httpHandler = new HTTPHandler(){
            @Override
            protected void onPostExecute(HTTPResponse response){
                if(response.getResult()){
                    testAnswers = JSONifier.jsonToTestAnswerList(response.getResponse());
                    btnStart.setEnabled(true);
                } else {
                    Toast.makeText(getBaseContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        String data = JSONifier.StringToJSON(new String[]{"session_id"},
                new String[]{String.valueOf(session.getId())});
        httpHandler.execute(POST_METHOD, API_URL + "/user-session/", data);
    }
}
