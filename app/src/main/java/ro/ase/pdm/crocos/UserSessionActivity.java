package ro.ase.pdm.crocos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entities.Answer;

public class UserSessionActivity extends AppCompatActivity {


    ListView listView;
    userQuestionsAdapter adapter;
    List<Answer> answers = new ArrayList<>();
    TextView openQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_session);
        listView = findViewById(R.id.lvAnswers);
        openQuestion = findViewById(R.id.tvQuestionOpen);
        initData();
        adapter = new userQuestionsAdapter(this,R.layout.user_question_item,answers);
        listView.setAdapter(adapter);
    }


    private void initData(){
        Answer a1 = new Answer();
        a1.setQuestion("How to compare 2 strings in Java?");
        a1.setCorrectAnswer("equals()");
        a1.setGivenAnswer("EQUALS()");
        a1.setOpen(true);
        a1.setFeedback("A good choice.");



        Answer a2 = new Answer();
        a2.setQuestion("How many hours a night do you sleep?");
        a2.setCorrectAnswer("4");
        a2.setGivenAnswer("8");
        a2.setOpen(false);
        a2.setFeedback("Sleeping well..");
        answers.add(a1);
        answers.add(a2);


    }
}
