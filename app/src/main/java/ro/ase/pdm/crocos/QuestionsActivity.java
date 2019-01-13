package ro.ase.pdm.crocos;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Test;
import utils.Constant;

public class QuestionsActivity extends AppCompatActivity implements Constant {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;
    Dialog myDialog;
    FloatingActionButton btn;
    private Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        myDialog = new Dialog(this);

        btn = findViewById(R.id.floatingActionButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });

        listView = (ExpandableListView)findViewById(R.id.lvExp);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHashMap);
        listView.setAdapter(listAdapter);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listDataHeader.add("Programming");
        listDataHeader.add("Economics");
        listDataHeader.add("Mathematics");
        listDataHeader.add("Statistics");


        List<String> programming = new ArrayList<>();
        programming.add("What is OOP?");
        programming.add("How many hours a night do you sleep?");

        List<String> economics = new ArrayList<>();
        economics.add("Who is Adam Smith?");

        List<String> mathematics = new ArrayList<>();
        mathematics.add("Is 64 a perfect square?");

        List<String> statistics = new ArrayList<>();
        statistics.add("What is Big Data?");

        listHashMap.put(listDataHeader.get(0),programming);
        listHashMap.put(listDataHeader.get(1),economics);
        listHashMap.put(listDataHeader.get(2),mathematics);
        listHashMap.put(listDataHeader.get(3),statistics);

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
