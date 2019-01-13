package ro.ase.pdm.crocos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
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
    private List<Category> categories = new ArrayList<>();
    private Spinner spinner;
    private ArrayAdapter<Category> adapter;
    private Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_test);

        super.onCreate(savedInstanceState);

        spinner = findViewById(R.id.spinnerCategory);

        adapter = new ArrayAdapter<>(CreateTestActivity.this,
                R.layout.category_spinner, categories);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

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

                        } else {
                            Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                String jsonData = extractData();

                if(!editing){
                    httpHandler.execute(POST_METHOD, API_URL + "/test", jsonData);
                    startActivityForResult(new Intent(CreateTestActivity.this, QuestionsActivity.class),
                            CREATE_TEST_REQUEST_CODE);
                } else{
                    httpHandler.execute(PUT_METHOD, API_URL + "/test", jsonData);
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

                    categories.clear();
                    categories.addAll(JSONifier.jsonToCategories(response.getResponse()));

                    Toast.makeText(getApplicationContext(), categories.get(0).getName(), Toast.LENGTH_SHORT)
                            .show();

                    adapter.notifyDataSetChanged();

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
                        test = JSONifier.jsonToTest(response.getResponse());
                        updateUI();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
            };
            httpHandler.execute(GET_METHOD, API_URL + "/test/" + testId);
        }
    }

    private void updateUI(){

        //we have the Test which contains a test, its category and its questions
        //you also have to send the received data in the questions activity
        //to not make another api call

    }

    public String extractData(){

        Test createTest = new Test();

        createTest.setName(findViewById(R.id.etName).toString());
        createTest.setDuration(Integer.parseInt(findViewById(R.id.etDuration).toString()));
        createTest.setQuestionsNo(Integer.parseInt(findViewById(R.id.etQuestionsNo).toString()));
        createTest.setRetries(Integer.parseInt(findViewById(R.id.etRetries).toString()));

        createTest.setCategory((Category)spinner.getSelectedItem());

        int tvBackwardsSelected = ((RadioGroup)findViewById(R.id.rgBackWards)).getCheckedRadioButtonId();
        RadioButton backwardsValue = (RadioButton) findViewById(tvBackwardsSelected);
        Boolean backwards = (backwardsValue.getText() == "Yes") ? true : false;
        createTest.setBackwards(backwards);

        int tvPrivacySelected = ((RadioGroup)findViewById(R.id.rgPrivacy)).getCheckedRadioButtonId();
        RadioButton privacyValue = (RadioButton) findViewById(tvPrivacySelected);
        Boolean privacy = (privacyValue.getText() == "Public") ? true : false;
        createTest.setPrivacy(privacy);

        return createTest.toJSON();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_TEST_REQUEST_CODE) {
            editing = true;
            loadCategories();
            checkIfEditing();
        }
    }
}
