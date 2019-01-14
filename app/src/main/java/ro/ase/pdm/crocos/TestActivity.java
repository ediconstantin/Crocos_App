package ro.ase.pdm.crocos;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


import java.util.ArrayList;

import entities.Category;
import entities.Test;

public class TestActivity extends AppCompatActivity {

    ListView listView;

    ArrayList<Test> allTests = new ArrayList<>();
    private TestAdapter testAdapter;

    private FloatingActionButton btnAddTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        listView = findViewById(R.id.lvTests);

        initData();

        testAdapter = new TestAdapter(TestActivity.this, R.layout.test_item, allTests);

        listView.setAdapter(testAdapter);

        btnAddTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });


    }

    private void initData(){
        Test t1 = new Test();
        t1.setName("OOP");
        Category c1 = new Category();
        c1.setName("Programming");
        t1.setCategory(c1);

        Test t2 = new Test();
        t2.setName("Java");
        Category c2 = new Category();
        c2.setName("Programming");
        t2.setCategory(c2);

        Test t3 = new Test();
        t3.setName("C#");
        Category c3 = new Category();
        c3.setName("Programming");
        t3.setCategory(c3);

        allTests.add(t1);
        allTests.add(t2);
        allTests.add(t3);


    }
}
