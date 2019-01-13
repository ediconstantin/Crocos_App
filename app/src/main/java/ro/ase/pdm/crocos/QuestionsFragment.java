package ro.ase.pdm.crocos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionsFragment extends Fragment {


    public QuestionsFragment() {
        // Required empty public constructor
    }

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_questions, container, false);

        listView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
        initData();
        listAdapter = new ExpandableListAdapter(this.getContext(),listDataHeader,listHashMap);
        listView.setAdapter(listAdapter);


        return rootView;
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
