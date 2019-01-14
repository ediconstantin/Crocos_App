package ro.ase.pdm.crocos;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import entities.Test;

public class CreateSessionActivity extends AppCompatActivity {

    private Spinner spinner;
    List<Test> tests;
    ArrayAdapter<Test> adapter;
    Button btnSaveSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        initData();
        spinner = findViewById(R.id.csSpinnerTests);
        btnSaveSession = findViewById(R.id.fabNewSession);
        adapter = new ArrayAdapter<>(this, R.layout.test_spinner,tests);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        btnSaveSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });



    }

    private void initData() {
        Test t1 = new Test();
        t1.setName("OOP");

        Test t2 = new Test();
        t2.setName("Java");
    }


}
