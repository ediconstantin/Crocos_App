package ro.ase.pdm.crocos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Feedback;
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
    private List<Feedback> allFeedback = new ArrayList<>();
    private List<Integer> allAnswers = new ArrayList<>();
    ArrayAdapter<Feedback> adapter;
    ArrayAdapter<Integer> correctAnsAdapter;
    EditText etQuestion, etAns1, etAns2, etAns3, etAns4;
    Button btnAddQuestion;
    Spinner spinnerFeedback, spinnerCorrectAnswer, spinnerCategoryPopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        myDialog = new Dialog(this);

        btn = findViewById(R.id.floatingActionButton);
        Button btnSave = findViewById(R.id.btnSave);

        //validation();
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

        btnSave.setOnClickListener(new View.OnClickListener() {
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

        Feedback f1 = new Feedback();
        f1.setName("Immediate");
        f1.setValue(1);

        Feedback f2 = new Feedback();
        f2.setName("Final");
        f2.setValue(2);

        Feedback f3 = new Feedback();
        f3.setName("After session");
        f3.setValue(3);

        allFeedback.add(f1);
        allFeedback.add(f2);
        allFeedback.add(f3);

        allAnswers.add(1);
        allAnswers.add(2);
        allAnswers.add(3);
        allAnswers.add(4);





        //getAllQuestions();

        //api request to get all the questions defined by the user
        //then based on the id of the questions received as prop in test
        //the questions that are already affiliated with the test will be checked
        //test = (Test)getIntent().getSerializableExtra(CURRENT_TEST);
        //existingQuestions.addAll(test.getQuestions());

        //when a questions will be created from the pop-up
        //it will be sent to the server
        //if its successful it's placed in the questions list from this activity as checked

        //questions will be sent to be affiliated with the test one by one
        //the finish button will only close the activity and will redirect to tests_activity
    }

    private void validation() {

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

    private void showPopUp() {
        TextView txtClose;
        myDialog.setContentView(R.layout.questions_pop_up);
        txtClose = myDialog.findViewById(R.id.tvClose);

        spinnerFeedback = myDialog.findViewById(R.id.spinnerFeedback);
        adapter = new ArrayAdapter<>(QuestionsActivity.this,
                R.layout.feedback_spinner, allFeedback);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerFeedback.setAdapter(adapter);

        spinnerCorrectAnswer = myDialog.findViewById(R.id.spinnerCorrectAns);
        correctAnsAdapter = new ArrayAdapter<>(QuestionsActivity.this,
                R.layout.feedback_spinner,allAnswers);

        correctAnsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerCorrectAnswer.setAdapter(correctAnsAdapter);

        spinnerCategoryPopUp = myDialog.findViewById(R.id.spinnerCategoryPopUp);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    public String extractDataFromPopUP(){
        Question createQuestion = new Question();

        createQuestion.setQuestion(((TextView)myDialog.findViewById(R.id.etQuestion)).getText().toString());
        createQuestion.setAns1(((TextView)myDialog.findViewById(R.id.etAns1)).getText().toString());
        createQuestion.setAns2(((TextView)myDialog.findViewById(R.id.etAns2)).getText().toString());
        createQuestion.setAns3(((TextView)myDialog.findViewById(R.id.etAns3)).getText().toString());
        createQuestion.setAns4(((TextView)myDialog.findViewById(R.id.etAns4)).getText().toString());
        createQuestion.setCorrect(spinnerCorrectAnswer.getSelectedItem().toString());
        createQuestion.setFeedback(((Feedback)spinnerFeedback.getSelectedItem()).getValue());
        createQuestion.setDuration(Integer.parseInt((((TextView)myDialog.findViewById(R.id.etQuestionDuration)).getText().toString())));

        int multipleSelected = ((RadioGroup)myDialog.findViewById(R.id.rgMultiple)).getCheckedRadioButtonId();
        RadioButton multipleValue = (RadioButton) findViewById(multipleSelected);
        int multiple = (multipleValue.getText() == "Yes") ? 1 : 0;
        createQuestion.setMultiple(multiple);

        int openSelected = ((RadioGroup)myDialog.findViewById(R.id.rgOpen)).getCheckedRadioButtonId();
        RadioButton openValue = (RadioButton) findViewById(openSelected);
        int open = (openValue.getText() == "Yes") ? 1 : 0;
        createQuestion.setOpen(open);

        return createQuestion.toJSON();
    }

    private void getAllQuestions() {
        @SuppressLint("StaticFieldLeak")
        HTTPHandler httpHandler = new HTTPHandler() {
            @Override
            protected void onPostExecute(HTTPResponse response) {
                if (response.getResult()) {
                    allQuestions = JSONifier.jsonToQuestions(response.getResponse());
                    checkExistingQuestions();
                } else {
                    Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        httpHandler.execute(GET_METHOD, API_URL + "/question");
    }

    private void checkExistingQuestions() {

        allQuestions.removeAll(existingQuestions);

        //the adapter data set is existingQuestions;
        //check all the checkboxes for the items present

        existingQuestions.addAll(allQuestions);
        //not the existing questions should have the questions received from the previous activity checked
        //and the remaining questions unchecked

        listViewAdapter.notifyDataSetChanged();
        disableCircle();
    }

    private void disableCircle() {
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
