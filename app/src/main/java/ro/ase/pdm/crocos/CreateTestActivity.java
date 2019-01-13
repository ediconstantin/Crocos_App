package ro.ase.pdm.crocos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import entities.Category;
import entities.Test;
import utils.Constant;
import utils.HTTPHandler;
import utils.HTTPResponse;
import utils.JSONifier;


public class CreateTestActivity extends AppCompatActivity implements Constant {

    private Button btn;
    private SharedPreferences sharedPreferences;
    private boolean editing;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);
        loadCategories();

        sharedPreferences = getSharedPreferences(Constant.NAIRU_PREFERENCES, MODE_PRIVATE);

        checkIfEditing();

        btn = findViewById(R.id.btnAdd);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("StaticFieldLeak")
                HTTPHandler httpHandler = new HTTPHandler(){
                    @Override
                    protected void onPostExecute(HTTPResponse response){
                        if(response.getResult()){
                            Map<String, String> jsonMap = JSONifier.SimpleJSONToMap(response.getResponse());
                            int testId = Integer.parseInt(jsonMap.get("testId"));

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt(CREATE_TEST_ID, testId);
                            editor.apply();

                            startActivityForResult(new Intent(CreateTestActivity.this, QuestionsActivity.class),
                                    CREATE_TEST_REQUEST_CODE);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                String jsonData = extractData();

                if(!editing){
                    httpHandler.execute(POST_METHOD, API_URL + "/test", jsonData);
                } else{
                    startActivity(new Intent(CreateTestActivity.this, QuestionsActivity.class));
                }
            }
        });
    }

    private void loadCategories(){
        @SuppressLint("StaticFieldLeak")
        HTTPHandler httpHandler = new HTTPHandler(){
            @Override
            protected void onPostExecute(HTTPResponse response){
                if(response.getResult()){
                    categories = JSONifier.jsonToCategories(response.getResponse());
                    //put data inside the categories element
                } else {
                    Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        httpHandler.execute(GET_METHOD, API_URL + "/category");
    }

    private void checkIfEditing(){
        Intent intent = getIntent();
        boolean editing = intent.getBooleanExtra("isEditing", false);

        if(editing){
            int testId = sharedPreferences.getInt(CREATE_TEST_ID, 0);

            @SuppressLint("StaticFieldLeak")
            HTTPHandler httpHandler = new HTTPHandler(){
                @Override
                protected void onPostExecute(HTTPResponse response){
                    if(response.getResult()){
                        //transform the test, questions and categories to elements
                        Map<String, String> jsonMap = JSONifier.SimpleJSONToMap(response.getResponse());
                        updateUI(jsonMap);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
            };

            httpHandler.execute(GET_METHOD, API_URL + "/test/" + testId);
        }
    }

    private void updateUI(Map<String, String> jsonValues){
        //the received string contains arrays so you have to modify the jsonifier methods
        //you also have to send the received data in the questions activity
        //to not make another api call
    }

    public String extractData(){

        Test createTest = new Test();

        createTest.setName(findViewById(R.id.etName).toString());
        //take the category from the array
        //createTest.setCategory(findViewById();
        createTest.setDuration(findViewById(R.id.etDuration).toString());
        createTest.setQuestionsNo(findViewById(R.id.etQuestionsNo).toString());
        createTest.setRetries(findViewById(R.id.etRetries).toString());

        //data from radio buttons

        return createTest.toJSON();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_TEST_REQUEST_CODE) {
            editing = true;
        }
    }



}
