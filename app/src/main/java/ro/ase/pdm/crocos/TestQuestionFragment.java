package ro.ase.pdm.crocos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import entities.TestAnswer;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestQuestionFragment extends Fragment {


    TextView tvQuestion;
    RadioButton rbA, rbB, rbC, rbD;

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

       takeTestActivity = (TakeTestActivity) getActivity();
       questionIndex = getArguments().getInt("index");
       testAnswer = takeTestActivity.testAnswers.get(questionIndex);

       tvQuestion.setText(testAnswer.getQuestion());

       rbA.setText(testAnswer.getAns1());
       rbB.setText(testAnswer.getAns2());
       rbC.setText((testAnswer.getAns3()));
       rbD.setText((testAnswer.getAns4()));

        String message=getArguments().getString("index");
        return view;
    }

}
