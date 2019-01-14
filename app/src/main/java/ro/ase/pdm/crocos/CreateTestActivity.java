package ro.ase.pdm.crocos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entities.Category;
import entities.GlobalVar;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);

        spinner = findViewById(R.id.spinnerCategory);

        adapter = new ArrayAdapter<>(CreateTestActivity.this,
                R.layout.category_spinner, categories);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        loadCategories();

        sharedPreferences = getSharedPreferences(Constant.NAIRU_PREFERENCES, MODE_PRIVATE);

        test = new Test();
        test.setId(0);

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

                            if(test.getId() == 0){
                                test.setId(Integer.parseInt(jsonMap.get("testId")));
                            }

                            Intent intent = new Intent(getBaseContext(), QuestionsActivity.class);
                            intent.putExtra(CURRENT_TEST, test);

                            startActivityForResult(intent, CREATE_TEST_REQUEST_CODE);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CreateTestActivity.this, QuestionsActivity.class));
                        }
                    }
                };

                String jsonData = extractData();

                if(!editing){
                    httpHandler.execute(POST_METHOD, API_URL + "/test", jsonData);
                } else{
                    httpHandler.execute(PUT_METHOD, API_URL + "/test", jsonData);
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
                    GlobalVar.setCategories(categories);

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
            //you have to send the test as intent everytime
            test = (Test)getIntent().getSerializableExtra(CURRENT_TEST);
            updateUI();
        }
    }

    private void updateUI(){

        ((TextView)findViewById(R.id.etName)).setText(test.getName());
        ((TextView)findViewById(R.id.etDuration)).setText(test.getDuration());
        ((TextView)findViewById(R.id.etQuestionsNo)).setText(test.getQuestionsNo());
        ((TextView)findViewById(R.id.etRetries)).setText(test.getRetries());

        spinner.setSelection(categories.indexOf(test.getCategory()));

        ((TextView)findViewById(R.id.etName)).setText(test.getName());

        if(test.getBackwards()){
            findViewById(R.id.rbYes).setSelected(true);
        } else {
            findViewById(R.id.rbNo).setSelected(false);
        }

        if(test.getPrivacy()){
            findViewById(R.id.rbPublic).setSelected(true);
        } else {
            findViewById(R.id.rbPublic).setSelected(false);
        }
    }

    public String extractData(){

        Test createTest = new Test();

        createTest.setName(((TextView)findViewById(R.id.etName)).getText().toString());

        createTest.setDuration(Integer.parseInt(((TextView)findViewById(R.id.etDuration)).getText().toString()));
        createTest.setQuestionsNo(Integer.parseInt(((TextView)findViewById(R.id.etDuration)).getText().toString()));
        createTest.setRetries(Integer.parseInt(((TextView)findViewById(R.id.etDuration)).getText().toString()));

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
        /*
        if (requestCode == CREATE_TEST_REQUEST_CODE) {
            editing = true;
            loadCategories();
            checkIfEditing();
        }*/
    }
}
