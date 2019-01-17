package ro.ase.pdm.crocos;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import entities.TestAnswer;
import utils.Constant;
import utils.HTTPHandler;
import utils.HTTPResponse;
import utils.JSONifier;


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
        final View view = inflater.inflate(R.layout.fragment_test_question, container, false);
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
               @SuppressLint("StaticFieldLeak")
               HTTPHandler httpHandler = new HTTPHandler(){
                   @Override
                   protected void onPostExecute(HTTPResponse response){
                       if(response.getResult()){
                           Toast.makeText(getContext(), "Answer updated", Toast.LENGTH_SHORT).show();
                       } else {
                           Toast.makeText(getContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                       }
                   }
               };

               String jsonData = JSONifier.StringToJSON(new String[]{"answer_id", "answer"},
                       new String[]{String.valueOf(testAnswer.getAnswerId()), rbC.getText().toString()});
               httpHandler.execute(Constant.PUT_METHOD, Constant.API_URL + "/answer", jsonData);
           }
       });

       rbB.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               rbA.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       @SuppressLint("StaticFieldLeak")
                       HTTPHandler httpHandler = new HTTPHandler(){
                           @Override
                           protected void onPostExecute(HTTPResponse response){
                               if(response.getResult()){
                                   Toast.makeText(getContext(), "Answer updated", Toast.LENGTH_SHORT).show();
                               } else {
                                   Toast.makeText(getContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                               }
                           }
                       };

                       String jsonData = JSONifier.StringToJSON(new String[]{"answer_id", "answer"},
                               new String[]{String.valueOf(testAnswer.getAnswerId()), rbB.getText().toString()});
                       httpHandler.execute(Constant.PUT_METHOD, Constant.API_URL + "/answer", jsonData);
                   }
               });
           }
       });

       rbC.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               rbA.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       @SuppressLint("StaticFieldLeak")
                       HTTPHandler httpHandler = new HTTPHandler(){
                           @Override
                           protected void onPostExecute(HTTPResponse response){
                               if(response.getResult()){
                                   Toast.makeText(getContext(), "Answer updated", Toast.LENGTH_SHORT).show();
                               } else {
                                   Toast.makeText(getContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                               }
                           }
                       };

                       String jsonData = JSONifier.StringToJSON(new String[]{"answer_id", "answer"},
                               new String[]{String.valueOf(testAnswer.getAnswerId()), rbD.getText().toString()});
                       httpHandler.execute(Constant.PUT_METHOD, Constant.API_URL + "/answer", jsonData);
                   }
               });
           }
       });

       rbD.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               rbA.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       @SuppressLint("StaticFieldLeak")
                       HTTPHandler httpHandler = new HTTPHandler(){
                           @Override
                           protected void onPostExecute(HTTPResponse response){
                               if(response.getResult()){
                                   Toast.makeText(getContext(), "Answer updated", Toast.LENGTH_SHORT).show();
                               } else {
                                   Toast.makeText(getContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                               }
                           }
                       };

                       Log.w("dada", rbA.getText().toString());
                       String jsonData = JSONifier.StringToJSON(new String[]{"answer_id", "answer"},
                               new String[]{String.valueOf(testAnswer.getAnswerId()), rbA.getText().toString()});
                       httpHandler.execute(Constant.PUT_METHOD, Constant.API_URL + "/answer", jsonData);
                   }
               });
           }
       });

       btnSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //
               takeTestActivity.timer.cancel();
               startActivity(new Intent(view.getContext(), JoinActivity.class));
           }
       });

        String message=getArguments().getString("index");
        return view;
    }

}
