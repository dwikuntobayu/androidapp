package com.mdwikuntobayu.androidbasic;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SixActivity extends AppCompatActivity implements View.OnClickListener {
    AutoCompleteTextView name_actxt;
    Button time_dial_btn, date_dial_btn;
    static final int DIALOG_ID = 0;
    static final int DIALOG_DATE_ID = 0;
    int hour_x, minute_x, year_x, date_x, day_x;
    String[] names = {
        "Tasimun", "Saminah", "Daru", "Kunto", "Astri"
    };
    TimePicker clock_tp;
    Button clock_btn;
    EditText date_picker_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        name_actxt = (AutoCompleteTextView)findViewById(R.id.name_actxt);
        ArrayAdapter names_arradap = new ArrayAdapter(this, android.R.layout.select_dialog_item, names);
        name_actxt.setThreshold(2);
        name_actxt.setAdapter(names_arradap);

        clock_btn = (Button)findViewById(R.id.clock_btn);
        time_dial_btn = (Button)findViewById(R.id.time_dial_btn);
        date_dial_btn = (Button)findViewById(R.id.date_dial_btn);
        date_picker_txt = (EditText)findViewById(R.id.date_picker_txt);

        clock_btn.setOnClickListener(this);
        time_dial_btn.setOnClickListener(this);
        date_dial_btn.setOnClickListener(this);
        date_picker_txt.setOnClickListener(this);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        date_x = cal.get(Calendar.DATE);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        hour_x = cal.get(Calendar.HOUR);
        minute_x = cal.get(Calendar.MINUTE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clock_btn:
                clock_tp = (TimePicker)findViewById(R.id.clock_tp);
                clock_tp.setIs24HourView(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(SixActivity.this, clock_tp.getHour() + " : " + clock_tp.getMinute(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SixActivity.this, clock_tp.getCurrentHour() + " : " + clock_tp.getCurrentMinute(), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.time_dial_btn:
                TimePickerDialog time_dialog = new TimePickerDialog(this, time_picker_listener, hour_x, minute_x, false);
                time_dialog.show();
                break;

            case R.id.date_dial_btn:
                DatePickerDialog date_dialog = new DatePickerDialog( this, date_picker_listener, year_x, date_x, day_x);
                date_dialog.show();
                break;

            case R.id.date_picker_txt:
                DatePickerDialog date_picker_dialog = new DatePickerDialog( this, date_picker_dial, year_x, date_x, day_x);
                date_picker_dialog.show();
                break;

            default:
                break;
        }
    }

    private TimePickerDialog.OnTimeSetListener time_picker_listener =
        new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(SixActivity.this, hourOfDay + " : " + minute, Toast.LENGTH_SHORT).show();
            }
        };

    private DatePickerDialog.OnDateSetListener date_picker_listener =
        new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                int month = selectedMonth + 1;
                Toast.makeText(SixActivity.this, selectedYear + " \n " + month + " \n " + selectedDay, Toast.LENGTH_SHORT).show();
            }
        };

    private DatePickerDialog.OnDateSetListener date_picker_dial =
        new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                int month = selectedMonth + 1;
                date_picker_txt.setText(selectedYear + " - " + month + " - " + selectedDay);
            }
        };

}
