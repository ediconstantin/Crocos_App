package ro.ase.pdm.crocos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import entities.Session;
import entities.Student;
import utils.Constant;
import utils.HTTPHandler;
import utils.HTTPResponse;
import utils.JSONifier;

import static utils.Constant.CURRENT_SESSION;

public class DetailedSessionActivity extends AppCompatActivity implements Constant {

    ListView lvStudents;
    List<Student> students = new ArrayList<>();
    StudentsListAdapter adapter;
    Button btnDelete, btnStop;
    Session session;
    TextView token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_session);

        lvStudents = findViewById(R.id.lvStudents);
        btnDelete = findViewById(R.id.btnDelete);
        btnStop = findViewById(R.id.btnStopSession);

        token = findViewById(R.id.tvToken);

        btnStop.setVisibility(View.INVISIBLE);
        btnDelete.setVisibility(View.INVISIBLE);

        adapter = new StudentsListAdapter(this, R.layout.student_item, students);

        lvStudents.setAdapter(adapter);

        initData();

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("StaticFieldLeak")
                HTTPHandler httpHandler = new HTTPHandler(){
                    @Override
                    protected void onPostExecute(HTTPResponse response){
                        if(response.getResult()){
                            startActivity(new Intent(getBaseContext(), SessionActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                httpHandler.execute(PUT_METHOD, API_URL + "/session/close/" + session.getId(), "");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("StaticFieldLeak")
                HTTPHandler httpHandler = new HTTPHandler(){
                    @Override
                    protected void onPostExecute(HTTPResponse response){
                        if(response.getResult()){
                            startActivity(new Intent(getBaseContext(), SessionActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                httpHandler.execute(DELETE_METHOD, API_URL + "/session/" + session.getId());
            }
        });

        lvStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                //open UserSessionActivity and send the Student object as intent
            }
        });

    }

    private void initData() {
        Intent intent = getIntent();
        session = (Session)intent.getSerializableExtra(CURRENT_SESSION);

        TextView title = findViewById(R.id.VTestName);
        TextView startDate = findViewById(R.id.VStartDate);
        TextView endDate = findViewById(R.id.VEndDate);

        String startDateString = convertDate(session.getStartDate());
        String endDateString = convertDate(session.getEndDate());

        @SuppressLint("StaticFieldLeak")
        HTTPHandler httpHandler = new HTTPHandler(){
            @Override
            protected void onPostExecute(HTTPResponse response){
                if(response.getResult()){
                    students.addAll(JSONifier.jsonToStudents(response.getResponse()));
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        title.setText(session.getTestName());
        token.setText(session.getToken());
        startDate.setText(startDateString);
        endDate.setText(endDateString);

        switch(session.getStatus()){
            case 0:
                btnDelete.setVisibility(View.VISIBLE);
                btnStop.setVisibility(View.INVISIBLE);
                lvStudents.setVisibility(View.INVISIBLE);
                break;
            case 1:

                token.setText("Token");
                token.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.INVISIBLE);
                btnStop.setVisibility(View.VISIBLE);
                lvStudents.setVisibility(View.INVISIBLE);
                break;
            case 2:
                lvStudents.setVisibility(View.VISIBLE);
                httpHandler.execute(GET_METHOD, API_URL + "/session/full/" + session.getId());
                btnDelete.setVisibility(View.INVISIBLE);
                btnStop.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private String convertDate(int unixDate){
        Date date = new Date(unixDate*1000L);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));

        return sdf.format(date);
    }
}
