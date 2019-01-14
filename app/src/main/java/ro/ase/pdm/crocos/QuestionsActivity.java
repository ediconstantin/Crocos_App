package ro.ase.pdm.crocos;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Question;
import entities.Test;
import utils.Constant;

public class QuestionsActivity extends AppCompatActivity implements Constant {


    ArrayList<Question> questions = new ArrayList<>();
    ListView listView;
    Dialog myDialog;
    FloatingActionButton btn;
    private Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        myDialog = new Dialog(this);

        btn = findViewById(R.id.floatingActionButton);

        initData();

        listView = findViewById(R.id.lv);

        ListViewAdapter listViewAdapter = new ListViewAdapter(this, R.layout.list_item, questions);
        listView.setAdapter(listViewAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });


    }

    private void initData() {

        Question q1 = new Question();
        q1.setQuestion("How many hours a night do you sleep?");
        Question q2 = new Question();
        q2.setQuestion("What is OOP?");
        Question q3 = new Question();
        q3.setQuestion("Who is Adam Smith?");
        Question q4 = new Question();
        q4.setQuestion("Is 64 a perfect square?");
        Question q5 = new Question();
        q5.setQuestion("What is Big Data?");
        Question q6 = new Question();
        q6.setQuestion("What is Big Data?");
        Question q7 = new Question();
        q7.setQuestion("What is Big Data?");
        Question q8 = new Question();
        q8.setQuestion("What is Big Data?");
        Question q9 = new Question();
        q9.setQuestion("What is Big Data?");
        Question q10 = new Question();
        q10.setQuestion("What is Big Data?");
        Question q11 = new Question();
        q11.setQuestion("What is Big Data?");
        Question q12 = new Question();
        q12.setQuestion("Is scroll view working on this layout?");

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
        questions.add(q6);
        questions.add(q7);
        questions.add(q8);
        questions.add(q9);
        questions.add(q10);
        questions.add(q11);
        questions.add(q12);


        //api request to get all the questions defined by the user
        //then based on the id of the questions received as prop in test
        //the questions that are already affiliated with the test will be checked
        test = (Test)getIntent().getSerializableExtra(CURRENT_TEST);

        //when a questions will be created from the pop-up
        //it will be sent to the server
        //if its successful it's placed in the questions list from this activity as checked

        //questions will be sent to be affiliated with the test one by one
        //the finish button will only close the activity and will redirect to tests_activity
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

}
