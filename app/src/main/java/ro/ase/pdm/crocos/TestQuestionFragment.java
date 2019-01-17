package ro.ase.pdm.crocos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import entities.TestAnswer;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestQuestionFragment extends Fragment {


    TextView tvQuestion;
    RadioButton rbA, rbB, rbC, rbD;

    Button btnSubmit;

    TestAnswer testAnswer;
    TakeTestActivity takeTestActivity;

    int questionIndex;

    public TestQuestionFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_question, container, false);
        tvQuestion = view.findViewById(R.id.tvQuestionFragmentValue);
        rbA = view.findViewById(R.id.rbA);
        rbB = view.findViewById(R.id.rbB);
        rbC = view.findViewById(R.id.rbC);
        rbD = view.findViewById(R.id.rbD);

        btnSubmit = view.findViewById(R.id.btnSubmit);

       takeTestActivity = (TakeTestActivity) getActivity();
       questionIndex = getArguments().getInt("index");
       testAnswer = takeTestActivity.testAnswers.get(questionIndex);

       tvQuestion.setText(testAnswer.getQuestion());

       rbA.setText(testAnswer.getAns1());
       rbB.setText(testAnswer.getAns2());
       rbC.setText((testAnswer.getAns3()));
       rbD.setText((testAnswer.getAns4()));
       if(questionIndex == takeTestActivity.testAnswers.size()-1){
           btnSubmit.setVisibility(View.VISIBLE);
       }

       rbA.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //
           }
       });


       rbB.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //
           }
       });

       rbC.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //
           }
       });

       rbD.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //
           }
       });



       
       btnSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               takeTestActivity.timer.cancel();
               //intent to studentUserSessionActivity;
           }
       });

        String message=getArguments().getString("index");
        return view;
    }

}
