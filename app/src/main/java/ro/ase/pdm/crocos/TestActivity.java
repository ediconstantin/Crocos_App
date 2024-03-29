package ro.ase.pdm.crocos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import entities.Category;
import entities.Test;
import utils.Constant;
import utils.HTTPHandler;
import utils.HTTPResponse;
import utils.JSONifier;

public class TestActivity extends AppCompatActivity implements Constant {

    ListView listView;

    List<Test> allTests = new ArrayList<>();
    private TestAdapter testAdapter;
    private FloatingActionButton btnAddTest;
    private Button btnCreateSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        listView = findViewById(R.id.lvTests);

        testAdapter = new TestAdapter(TestActivity.this, R.layout.test_item, allTests);

        listView.setAdapter(testAdapter);

        initData();

        btnCreateSession = findViewById(R.id.btnCreateSession);
        btnCreateSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        btnAddTest = findViewById(R.id.fabAdd);

        btnAddTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateTestActivity.class));
            }
        });

        listView.setLongClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Intent intent = new Intent(getBaseContext(), CreateSessionActivity.class);
                Test sendTest = (Test)listView.getItemAtPosition(position);
                intent.putExtra(CURRENT_TEST, sendTest);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Test sendTest = (Test)listView.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(), CreateTestActivity.class);
                intent.putExtra(EDITING_MARK, true);
                intent.putExtra(CURRENT_TEST, sendTest);
                startActivity(intent);

                return true;
            }
        });
    }

    private void initData(){
        loadTests();
    }

    private void loadTests(){
        @SuppressLint("StaticFieldLeak")
        HTTPHandler httpHandler = new HTTPHandler(){
            @Override
            protected void onPostExecute(HTTPResponse response){
                if(response.getResult()){
                    allTests.addAll(JSONifier.jsonToTests(response.getResponse()));
                    testAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        httpHandler.execute(GET_METHOD, API_URL + "/test");
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.NAIRU_PREFERENCES, MODE_PRIVATE);
        Boolean isProf = Boolean.parseBoolean(sharedPreferences.getString(Constant.IS_PROF, "0"));

        if(isProf){
            startActivity(new Intent(this, TeacherActivity.class));
        } else {
            startActivity(new Intent(this, StudentActivity.class));
        }
    }
}
