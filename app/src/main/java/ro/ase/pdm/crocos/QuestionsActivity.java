package ro.ase.pdm.crocos;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Question;
import entities.Test;
import utils.Constant;
import utils.HTTPHandler;
import utils.HTTPResponse;
import utils.JSONifier;

public class QuestionsActivity extends AppCompatActivity implements Constant {


    ArrayList<Question> existingQuestions = new ArrayList<>();
    ListView listView;
    Dialog myDialog;
    FloatingActionButton btn;
    private Test test;
    private ListViewAdapter listViewAdapter;
    private List<Question> allQuestions;
    EditText etQuestion, etAns1, etAns2, etAns3,etAns4;
    Button btnAddQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        myDialog = new Dialog(this);

        btn = findViewById(R.id.floatingActionButton);
        Button btnSave = findViewById(R.id.btnSave);

        validation();
        initData();

        listView = findViewById(R.id.lv);

        listViewAdapter = new ListViewAdapter(this, R.layout.list_item, existingQuestions);
        listView.setAdapter(listViewAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //redirect to the TestsActivity
            }
        });

        //event listener for clicking on a checkbox on the questions list
        //if the checkbox is present, it will be removed from test
        //else, it will be added

    }

    private void initData() {

        Question q1 = new Question();
        q1.setQuestion("How many hours a night do you sleep?");

        existingQuestions.add(q1);
        getAllQuestions();

        //api request to get all the questions defined by the user
        //then based on the id of the questions received as prop in test
        //the questions that are already affiliated with the test will be checked
        test = (Test)getIntent().getSerializableExtra(CURRENT_TEST);
        existingQuestions.addAll(test.getQuestions());

        //when a questions will be created from the pop-up
        //it will be sent to the server
        //if its successful it's placed in the questions list from this activity as checked

        //questions will be sent to be affiliated with the test one by one
        //the finish button will only close the activity and will redirect to tests_activity
    }

    private void validation(){
        etQuestion = findViewById(R.id.etQuestion);
        etAns1 = findViewById(R.id.etAns1);
        etAns2 = findViewById(R.id.etAns2);
        etAns3 = findViewById(R.id.etAns3);
        etAns4 = findViewById(R.id.etAns4);
        btnAddQuestion = findViewById(R.id.btnAddQuestion);

        etQuestion.addTextChangedListener(textWatcher);
        etAns1.addTextChangedListener(textWatcher);
        etAns2.addTextChangedListener(textWatcher);
        etAns3.addTextChangedListener(textWatcher);
        etAns4.addTextChangedListener(textWatcher);

    }

    private void showPopUp(){
        TextView txtClose;
        myDialog.setContentView(R.layout.questions_pop_up);
        txtClose = myDialog.findViewById(R.id.tvClose);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    private void getAllQuestions(){
        @SuppressLint("StaticFieldLeak")
        HTTPHandler httpHandler = new HTTPHandler(){
            @Override
            protected void onPostExecute(HTTPResponse response){
                if(response.getResult()){
                    allQuestions = JSONifier.jsonToQuestions(response.getResponse());
                    checkExistingQuestions();
                } else {
                    Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        httpHandler.execute(GET_METHOD, API_URL + "/question");
    }

    private void checkExistingQuestions(){

        allQuestions.removeAll(existingQuestions);

        //the adapter data set is existingQuestions;
        //check all the checkboxes for the items present

        existingQuestions.addAll(allQuestions);
        //not the existing questions should have the questions received from the previous activity checked
        //and the remaining questions unchecked

        listViewAdapter.notifyDataSetChanged();
        disableCircle();
    }

    private void disableCircle(){
        //this function will hide the rotating circle
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String questionInput = etQuestion.getText().toString();
            String ans1Input = etAns1.getText().toString();
            String ans2Input = etAns2.getText().toString();
            String ans3Input = etAns3.getText().toString();
            String ans4Input = etAns4.getText().toString();

            btnAddQuestion.setEnabled(!questionInput.isEmpty() && !ans1Input.isEmpty()
                    && !ans2Input.isEmpty() && !ans3Input.isEmpty() && !ans4Input.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
