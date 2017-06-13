package com.example.emiliekvist.kvitit;

import android.app.Activity;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by EmilieKvist on 13-06-2017.
 */

public class AddReceiptActivity extends Activity implements OnDateSetListener {

    private Button date;
    private Button endDate;

    private DatePickerDialog dateDialog;
    private DatePickerDialog endDateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_receipt);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        if (month < 12) {
            month++;
        } else {
            month = 1;
        }
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateDialog = new DatePickerDialog(this, AddReceiptActivity.this, year, month, day);
        endDateDialog = new DatePickerDialog(this, AddReceiptActivity.this, year+2, month, day);

        date = (Button) findViewById(R.id.date);
        date.setText(day + "/" + month + "-" + year);
        endDate = (Button) findViewById(R.id.endDate);
        year = year+2;
        endDate.setText(day + "/" + month + "-" + year);

        date.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
                dateDialog.setOnDateSetListener(new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + month + "-" + year);
                        year = year + 2;
                        endDate.setText(dayOfMonth + "/" + month + "-" + year);
                    }
                });
                /*int year = dateDialog.getDatePicker().getYear();
                int month = dateDialog.getDatePicker().getMonth();
                if (month < 12) {
                    month++;
                } else {
                    month = 1;
                }
                int day = dateDialog.getDatePicker().getDayOfMonth();*/
                //onDateSet(dateDialog.getDatePicker(), dateDialog.getDatePicker().getDayOfMonth(),
                  //      dateDialog.getDatePicker().getMonth(), dateDialog.getDatePicker().getYear());
            }
        });

        endDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endDateDialog.show();
                endDateDialog.setOnDateSetListener(new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate.setText(dayOfMonth + "/" + month + "-" + year);
                    }
                });
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    }
}
