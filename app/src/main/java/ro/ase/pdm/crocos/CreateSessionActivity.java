package ro.ase.pdm.crocos;

import android.app.DatePickerDialog;
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

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entities.Test;

public class CreateSessionActivity extends AppCompatActivity {

    private Spinner spinner;
    List<Test> tests;
    ArrayAdapter<Test> adapter;
    Button btnSaveSession;

    private TextView displayDate,startHour, endHour;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private String sDate, eDate;
    private Date startDate;
    private Date endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        initData();
        spinner = findViewById(R.id.csSpinnerTests);
        btnSaveSession = findViewById(R.id.btnSaveSession);
        startHour = findViewById(R.id.etStartHour);
        endHour = findViewById(R.id.etEndtHour);

        adapter = new ArrayAdapter<>(this, R.layout.test_spinner,tests);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        displayDate = (TextView)findViewById(R.id.tvSelectDateClickable);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(CreateSessionActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener,day,month,year);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                sDate = dayOfMonth +"/" + month +"/"  + year;
                eDate = dayOfMonth +"/" + month +"/"  + year;
            }
        };

        btnSaveSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDate = sDate +" " + startHour.getText().toString().trim() +":00";
                eDate = eDate + " " + endHour.getText().toString().trim();

                try {
                    startDate = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss").parse(sDate);
                    endDate = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss").parse(eDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Intent to the session activity

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
