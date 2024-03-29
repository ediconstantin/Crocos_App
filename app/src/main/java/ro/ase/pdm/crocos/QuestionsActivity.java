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
import android.widget.AdapterView;
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

import entities.Category;
import entities.Feedback;
import entities.GlobalVar;
import entities.Question;
import entities.Test;
import entities.questionAnswer;
import utils.Constant;
import utils.HTTPHandler;
import utils.HTTPResponse;
import utils.JSONifier;

public class QuestionsActivity extends AppCompatActivity implements Constant {


    List<Question> existingQuestions = new ArrayList<>();
    ListView listView, listViewOther;
    Dialog myDialog;
    FloatingActionButton btn;
    private Test test;
    private ListViewAdapter listViewAdapter;
    private OtherQuestionsAdapter otherQuestionsAdapter;
    private List<Question> allQuestions = new ArrayList<>();
    private List<questionAnswer> allAnswers = new ArrayList<>();
    private List<Category> categories;
    ArrayAdapter<questionAnswer> correctAnsAdapter;
    ArrayAdapter<Category> categoryAdapter;
    EditText etQuestion, etAns1, etAns2, etAns3, etAns4;
    Button btnAddQuestion;
    Spinner spinnerCorrectAnswer, spinnerCategoryPopUp;
    Question createQuestion;
    Question currentQuestion;

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
        listViewOther = findViewById(R.id.lvOther);

        listViewAdapter = new ListViewAdapter(this, R.layout.list_item, existingQuestions);
        listView.setAdapter(listViewAdapter);

        otherQuestionsAdapter = new OtherQuestionsAdapter(this, R.layout.list_item, allQuestions);
        listViewOther.setAdapter(otherQuestionsAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), TestActivity.class));
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                @SuppressLint("StaticFieldLeak")
                HTTPHandler httpHandler = new HTTPHandler(){
                    @Override
                    protected void onPostExecute(HTTPResponse response){
                        if(response.getResult()){
                            existingQuestions.remove(currentQuestion);
                            allQuestions.add(currentQuestion);
                            listViewAdapter.notifyDataSetChanged();
                            otherQuestionsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                currentQuestion = (Question)listView.getItemAtPosition(position);

                httpHandler.execute(DELETE_METHOD, API_URL + "/test/question/"
                        + test.getId() + "/" + currentQuestion.getId());
            }
        });

        listViewOther.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                @SuppressLint("StaticFieldLeak")
                HTTPHandler httpHandler = new HTTPHandler(){
                    @Override
                    protected void onPostExecute(HTTPResponse response){
                        if(response.getResult()){
                            existingQuestions.add(currentQuestion);
                            allQuestions.remove(currentQuestion);
                            listViewAdapter.notifyDataSetChanged();
                            otherQuestionsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                currentQuestion = (Question)listViewOther.getItemAtPosition(position);

                String jsonData = JSONifier.StringToJSON(new String[]{"questionId"},
                        new String[]{String.valueOf(currentQuestion.getId())});

                httpHandler.execute(POST_METHOD, API_URL + "/test/question/" + test.getId(), jsonData);
            }
        });

    }

    private void initData() {

        allAnswers.add(new questionAnswer(1));
        allAnswers.add(new questionAnswer(2));
        allAnswers.add(new questionAnswer(3));
        allAnswers.add(new questionAnswer(4));

        categories = GlobalVar.getCategories();

        getAllQuestions();

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


        spinnerCorrectAnswer = myDialog.findViewById(R.id.spinnerCorrectAns);
        correctAnsAdapter = new ArrayAdapter<>(QuestionsActivity.this,
                R.layout.feedback_spinner,allAnswers);

        correctAnsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerCorrectAnswer.setAdapter(correctAnsAdapter);

        spinnerCategoryPopUp = myDialog.findViewById(R.id.spinnerCategoryPopUp);
        categoryAdapter = new ArrayAdapter<>(QuestionsActivity.this, R.layout.q_category_spinner,
                                                                                            categories);
        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerCategoryPopUp.setAdapter(categoryAdapter);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.show();

        btnAddQuestion = myDialog.findViewById(R.id.btnAddQuestion);

        btnAddQuestion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                @SuppressLint("StaticFieldLeak")
                HTTPHandler httpHandler = new HTTPHandler(){
                    @Override
                    protected void onPostExecute(HTTPResponse response){
                        if(response.getResult()){
                            Map<String, String> jsonMap = JSONifier.SimpleJSONToMap(response.getResponse());
                            createQuestion.setId(Integer.parseInt(jsonMap.get("questionId")));
                            existingQuestions.add(0, createQuestion);
                            listViewAdapter.notifyDataSetChanged();
                            myDialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                String jsonData = extractDataFromPopUP();
                httpHandler.execute(POST_METHOD, API_URL + "/question/test/" + test.getId(), jsonData);
            }
        });
    }

    private void populatePopUp(){

    }

    public String extractDataFromPopUP(){
        createQuestion = new Question();

        createQuestion.setQuestion(((TextView)myDialog.findViewById(R.id.etQuestion)).getText().toString());
        createQuestion.setAns1(((TextView)myDialog.findViewById(R.id.etAns1)).getText().toString());
        createQuestion.setAns2(((TextView)myDialog.findViewById(R.id.etAns2)).getText().toString());
        createQuestion.setAns3(((TextView)myDialog.findViewById(R.id.etAns3)).getText().toString());
        createQuestion.setAns4(((TextView)myDialog.findViewById(R.id.etAns4)).getText().toString());
        createQuestion.setCorrect(((questionAnswer)spinnerCorrectAnswer.getSelectedItem()).getAnswerString());
        createQuestion.setFeedback(((TextView)myDialog.findViewById(R.id.etFeedbackQuestion)).getText().toString());
        createQuestion.setDuration(Integer.parseInt((((TextView)myDialog.findViewById(R.id.etQuestionDuration)).getText().toString())));

        int multipleSelected = ((RadioGroup)myDialog.findViewById(R.id.rgMultiple)).getCheckedRadioButtonId();
        //RadioButton multipleValue = ((RadioButton)myDialog.findViewById(multipleSelected));
        String multipleValue = ((RadioButton)myDialog.findViewById(multipleSelected)).getText().toString();
        int multiple = (multipleValue.equals("Yes")) ? 1 : 0;
        createQuestion.setMultiple(multiple);

        int openSelected = ((RadioGroup)myDialog.findViewById(R.id.rgOpen)).getCheckedRadioButtonId();
        RadioButton openValue = (RadioButton)myDialog.findViewById(openSelected);
        int open = (openValue.getText() == "Yes") ? 1 : 0;
        createQuestion.setOpen(open);

        createQuestion.setCategory((Category)spinnerCategoryPopUp.getSelectedItem());
        return createQuestion.toJSON();
    }

    private void getAllQuestions() {
        @SuppressLint("StaticFieldLeak")
        HTTPHandler httpHandler = new HTTPHandler() {
            @Override
            protected void onPostExecute(HTTPResponse response) {
                if (response.getResult()) {

                    test = (Test)getIntent().getSerializableExtra(CURRENT_TEST);

                    existingQuestions.addAll(test.getQuestions());

                    allQuestions.addAll(JSONifier.jsonToQuestions(response.getResponse()));

                    checkExistingQuestions();
                } else {
                    Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        httpHandler.execute(GET_METHOD, API_URL + "/question");
    }

    private void checkExistingQuestions() {

        listViewAdapter.notifyDataSetChanged();

        allQuestions.removeAll(existingQuestions);

        otherQuestionsAdapter.notifyDataSetChanged();

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

    @Override
    public void onBackPressed() {
        test.setQuestions(existingQuestions);
        Intent intent = new Intent();
        intent.putExtra(TEST_WITH_QUESTIONS, test);
        setResult(CREATE_TEST_REQUEST_CODE, intent);
        super.onBackPressed();
    }

}
