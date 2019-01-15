package ro.ase.pdm.crocos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import entities.Student;

public class DetailedSessionActivity extends AppCompatActivity {

    ListView lvStudents;
    List<Student> students = new ArrayList<>();
    StudentsListAdapter adapter;
    Button btnDelete, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_session);

        lvStudents = findViewById(R.id.lvStudents);
        btnDelete = findViewById(R.id.btnDelete);
        btnStop = findViewById(R.id.btnStopSession);

        btnStop.setVisibility(View.INVISIBLE);
        btnDelete.setVisibility(View.INVISIBLE);

        initData();

        adapter = new StudentsListAdapter(this, R.layout.student_item, students);

        lvStudents.setAdapter(adapter);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

    }

    private void initData() {
        Student s1 = new Student();
        s1.setFirstName("Alexandru");
        s1.setLastName("Bisag");
        s1.setGroup("1066");

        Student s2 = new Student();
        s2.setFirstName("Alexandru");
        s2.setLastName("Demidov");
        s2.setGroup("1069");

        students.add(s1);
        students.add(s2);
    }
}
