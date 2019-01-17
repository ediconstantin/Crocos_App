package ro.ase.pdm.crocos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import entities.UserSession;

public class studentUserSessionsActivity extends AppCompatActivity {


    ListView listView;
    studentUserSessionAdapter adapter;
    List<UserSession> sessions= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_user_sessions);

        listView = findViewById(R.id.lvUserSession);
        initData();

        adapter = new studentUserSessionAdapter(this, R.layout.user_session_item, sessions);

        listView.setAdapter(adapter);
    }

    private void initData(){
        UserSession s1 = new UserSession();
        s1.setTestName("OOP");
        s1.setResult(5);

        UserSession s2 = new UserSession();
        s2.setTestName("Android");
        s2.setResult(7);

        UserSession s3 = new UserSession();
        s3.setTestName("Web-tech");
        s3.setResult(6);

        sessions.add(s1);
        sessions.add(s2);
        sessions.add(s3);
    }
}
