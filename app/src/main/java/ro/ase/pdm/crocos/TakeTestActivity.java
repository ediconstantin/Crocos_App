package ro.ase.pdm.crocos;

import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import entities.Test;
import entities.TestAnswer;
import entities.TestAnswerList;
import utils.Constant;

public class TakeTestActivity extends AppCompatActivity implements Constant {

    private TextView tvCountDown;
    public CountDownTimer timer;
    private long timeLeftInMilliSeconds = 15000;
    private boolean timeIsRunning;
    TestQuestionAdapter adapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    Dialog myDialog;
    ProgressBar progressBar;

    List<TestQuestionFragment> fragments = new ArrayList<>();
    public List<TestAnswer> testAnswers = new ArrayList<>();
    TestAnswerList testAnswerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        testAnswerList = (TestAnswerList)intent.getSerializableExtra(JSON_TEST_TAKEN);
        int duration = intent.getIntExtra(DURATION, 0);
        timeLeftInMilliSeconds= duration;

        Log.w("LALALA", String.valueOf(duration));

        setContentView(R.layout.activity_take_test);

        myDialog = new Dialog(this);

        tvCountDown = findViewById(R.id.tvCountDown);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        progressBar = findViewById(R.id.progressBar);
        initList();
        genFragmentList();


        adapter = new TestQuestionAdapter(getSupportFragmentManager(), this, fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        startTimer();

        tvCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });
    }

    private void startTimer() {
        timer = new CountDownTimer(timeLeftInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliSeconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

                tvCountDown.setTextColor(getResources().getColor(R.color.redEndColor));
                showPopUp();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }.start();
        timeIsRunning = true;
    }

    private void showPopUp(){
        Button btnOK;
        myDialog.setContentView(R.layout.time_expired_pop_up);
        btnOK = myDialog.findViewById(R.id.btnOk);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), JoinActivity.class));
                finish();
            }
        });
        myDialog.show();

    }

    private void updateTimer() {
        int minutes = (int) (timeLeftInMilliSeconds / 1000) / 60;
        int seconds = (int) (timeLeftInMilliSeconds / 1000) % 60;

        String timeLeftText = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        tvCountDown.setText(timeLeftText);
    }

    private void initList(){
        Log.e("LALALA", String.valueOf(testAnswerList.getTestAnswers().size()));
        testAnswers = testAnswerList.getTestAnswers();
    }
    private void initDate(){

        TestAnswer t1 = new TestAnswer();
        t1.setQuestion("Ce structura este masivul?");
        t1.setAns1("Recursiva");
        t1.setAns2("Omogena cu acces secvential");
        t1.setAns3("Omogena cu acces direct");
        t1.setAns4("Eterogena cu acces direct");

        TestAnswer t2 = new TestAnswer();
        t2.setQuestion("Un algoritm iterativ este:");
        t2.setAns1("Algoritm ce se autoapeleaza");
        t2.setAns2("Proces repetitiv static");
        t2.setAns3("Proces repetitiv dinamic");
        t2.setAns4("Proces repetitiv in care variabiala nu depinde");

        TestAnswer t3 = new TestAnswer();
        t3.setQuestion("Tipul unei functii rezulta din:");
        t3.setAns1("Forma sintactica");
        t3.setAns2("Declaratie");
        t3.setAns3("Tipurile operanzilor");
        t3.setAns4("Declaratia functiei");

        TestAnswer t4 = new TestAnswer();
        t4.setQuestion("Tipul unei functii rezulta din:");
        t4.setAns1("Forma sintactica");
        t4.setAns2("Declaratie");
        t4.setAns3("Tipurile operanzilor");
        t4.setAns4("Declaratia functiei");

        TestAnswer t5 = new TestAnswer();
        t5.setQuestion("Tipul unei functii rezulta din:");
        t5.setAns1("Forma sintactica");
        t5.setAns2("Declaratie");
        t5.setAns3("Tipurile operanzilor");
        t5.setAns4("Declaratia functiei");



        testAnswers.add(t1);
        testAnswers.add(t2);
        testAnswers.add(t3);
        testAnswers.add(t4);
        testAnswers.add(t5);
    }

    private void genFragmentList(){
        for(int i=0; i < testAnswers.size();i++){
            Bundle bundle = new Bundle();
            bundle.putInt("index",i);
            TestQuestionFragment fragment = new TestQuestionFragment();
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
    }


}
