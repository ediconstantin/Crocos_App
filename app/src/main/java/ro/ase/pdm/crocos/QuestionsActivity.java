package ro.ase.pdm.crocos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

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
    }

}
