package ro.ase.pdm.crocos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        listView = findViewById(R.id.lvTests);

        initData();

        testAdapter = new TestAdapter(TestActivity.this, R.layout.test_item, allTests);

        listView.setAdapter(testAdapter);

        btnAddTest = findViewById(R.id.fabAdd);

        btnAddTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateTestActivity.class));
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
}
