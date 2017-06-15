package com.example.emiliekvist.kvitit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by EmilieKvist on 13-06-2017.
 */

public class AddReceiptActivity extends Activity implements OnDateSetListener {

    private Button date;
    private Button endDate;

    private Button add;
    private Button cancel;

    private TextView tagsView;

    private DatePickerDialog dateDialog;
    private DatePickerDialog endDateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_receipt);

        // dato og udløbsdato datepickers
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
        endDateDialog = new DatePickerDialog(this, AddReceiptActivity.this, year + 2, month, day);

        date = (Button) findViewById(R.id.date);
        date.setText(day + "-" + month + "-" + year);
        date.setTextSize(20);
        endDate = (Button) findViewById(R.id.endDate);
        year = year + 2;
        endDate.setText(day + "-" + month + "-" + year);
        endDate.setTextSize(20);

        date.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
                dateDialog.setOnDateSetListener(new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        int thisYear = calendar.get(Calendar.YEAR);
                        int thisMonth = calendar.get(Calendar.MONTH);
                        if (thisMonth < 12) {
                            thisMonth++;
                        } else {
                            thisMonth = 1;
                        }
                        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
                        if (year < thisYear ||
                                (year == thisYear && month < thisMonth) ||
                                (year == thisYear && month == thisMonth && dayOfMonth <= thisDay)) {
                            date.setText(dayOfMonth + "-" + month + "-" + year);
                            date.setTextSize(20);
                            year = year + 2;
                            endDate.setText(dayOfMonth + "-" + month + "-" + year);
                            endDate.setTextSize(20);
                        } else {
                            date.setText(thisDay + "-" + thisMonth + "-" + thisYear);
                            date.setTextSize(20);
                            thisYear = thisYear + 2;
                            endDate.setText(thisDay + "-" + thisMonth + "-" + thisYear);
                            endDate.setTextSize(20);
                        }
                    }
                });
            }
        });

        endDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endDateDialog.show();
                endDateDialog.setOnDateSetListener(new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ourDate = date.getText().toString();
                        String[] ourDateSp = ourDate.split("-");
                        int ourDay = Integer.parseInt(ourDateSp[0]);
                        int ourMonth = Integer.parseInt(ourDateSp[1]);
                        int ourYear = Integer.parseInt(ourDateSp[2]);
                        if (ourYear < year ||
                                (year == ourYear && ourMonth < month) ||
                                (year == ourYear && month == ourMonth && ourDay <= dayOfMonth)) {
                            endDate.setText(dayOfMonth + "-" + month + "-" + year);
                            endDate.setTextSize(20);
                        } else {
                            ourYear = ourYear + 2;
                            endDate.setText(ourDay + "-" + ourMonth + "-" + ourYear);
                            endDate.setTextSize(20);
                        }
                    }
                });
            }
        });

        // tags menu og visning
        tagsView = (TextView) findViewById(R.id.tags_box);
        Spinner tags = (Spinner) findViewById(R.id.tags_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tags_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tags.setAdapter(adapter);
        tags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tag = parent.getSelectedItem().toString();
                String temp = tagsView.getText().toString();
                Log.i("AddR", temp);
                if (temp.equals("Vælg tags ")) {
                    temp = "";
                }
                temp = temp + tag + " ";
                tagsView.setText(temp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // fortryd knap setup
        cancel = (Button) findViewById(R.id.fortryd_button);

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(AddReceiptActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        // tilføj knap setup
        add = (Button) findViewById(R.id.tilføj_button);

        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // get date
                String ourDate = date.getText().toString();
                String[] ourDateSp = ourDate.split("-");
                int ourDay = Integer.parseInt(ourDateSp[0]);
                int ourMonth = Integer.parseInt(ourDateSp[1]);
                int ourYear = Integer.parseInt(ourDateSp[2]);
                // get endDate
                String ourEndDate = endDate.getText().toString();
                String[] ourEndDateSp = ourDate.split("-");
                int ourEndDay = Integer.parseInt(ourEndDateSp[0]);
                int ourEndMonth = Integer.parseInt(ourEndDateSp[1]);
                int ourEndYear = Integer.parseInt(ourEndDateSp[2]);
                // get tags
                String tagStr = tagsView.getText().toString();
                String[] tagStrSp = tagStr.split(" ");
                // get current photo

            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    }
}
