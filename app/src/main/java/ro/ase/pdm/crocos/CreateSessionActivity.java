package ro.ase.pdm.crocos;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entities.Test;
import utils.Constant;
import utils.HTTPHandler;
import utils.HTTPResponse;
import utils.JSONifier;

public class CreateSessionActivity extends AppCompatActivity implements Constant {


    private Spinner spinner;
    List<Test> tests = new ArrayList<>();
    Test test;
    ArrayAdapter<Test> adapter;
    Button btnSaveSession;

    private TextView displayDate,tvEndDate ,startHour, endHour;
    private DatePickerDialog.OnDateSetListener startDateSetListener, endDateSetListener;

    private String sDate, eDate;
    private Date startDate;
    private Date endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        initData();

        btnSaveSession = findViewById(R.id.btnSaveSession);
        startHour = findViewById(R.id.etStartHour);
        endHour = findViewById(R.id.etEndtHour);

        displayDate = (TextView)findViewById(R.id.tvSelectDateClickable);
        tvEndDate = findViewById(R.id.tvSelectEndDateClickable);

        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalendar(startDateSetListener);
            }

        });

        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalendar(endDateSetListener);
            }
        });

        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                sDate = dayOfMonth + " " + month + " " + year;
                displayDate.setText(sDate);
            }
        };

        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                eDate = dayOfMonth + " " + month + " "  + year;
                tvEndDate.setText(eDate);
            }
        };

        btnSaveSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportData();
            }
        });
    }

    private void displayCalendar(DatePickerDialog.OnDateSetListener listener){
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(CreateSessionActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth, listener,day,month,year);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void exportData(){
        String csDate = sDate + " " + startHour.getText().toString().trim() +":00";
        String esDate = eDate + " " + endHour.getText().toString().trim() + ":00";

        @SuppressLint("StaticFieldLeak")
        HTTPHandler httpHandler = new HTTPHandler(){
            @Override
            protected void onPostExecute(HTTPResponse response){
                if(response.getResult()){
                    //startActivity(new Intent(getBaseContext(), SessionActivity.class));
                    //finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error - " + response.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        String jsonData = JSONifier.StringToJSON(new String[]{"test_id", "start_hour", "end_hour"},
                new String[]{String.valueOf(test.getId()), csDate, esDate});

        httpHandler.execute(POST_METHOD, API_URL + "/session", jsonData);
    }

    private void initData() {
        Intent intent = getIntent();
        test = (Test)intent.getSerializableExtra(CURRENT_TEST);
    }


}
