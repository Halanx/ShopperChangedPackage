package com.shopperapphalanx.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.shopperapphalanx.R;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class Schedule extends AppCompatActivity implements DatePickerListener {

    Calendar startDate,endDate;

    RadioGroup group,rg1,rg2,rg3;

    RadioButton t1,t2,t3,t4,t5,t6;
    HorizontalCalendar horizontalCalendar;
    String date_selected,time_selected;

    String starttime,endtime;

    private Boolean flagmale = false;
    Boolean flagselected = false;

    Button done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, 10);

/** start before 1 month from now */
        startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 0);

        done = (Button) findViewById(R.id.button_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = getSharedPreferences("Login", Context.MODE_PRIVATE).getString("MobileNumber", null);

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="https://api.halanx.com/shoppers/detail/";

                // Request a string response from the provided URL.
                JSONObject obj = new JSONObject();
                try {
                    starttime = time_selected.substring(0, 5);
                    endtime = time_selected.substring(6, 11);
                    Log.d("done", "done");
                    obj.put("AvailableDate", date_selected);
                    obj.put("AvailableFrom", starttime);
                    obj.put("AvailableTo", endtime);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Volley.newRequestQueue(getApplicationContext()).add(new JsonObjectRequest(Request.Method.PATCH, url, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("TAG", "ONLINE");
                        startActivity(new Intent(Schedule.this, HomeActivity.class));

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        params.put("Authorization", getApplicationContext().getSharedPreferences("Tokenkey",Context.MODE_PRIVATE).getString("token",null));
                        return params;
                    }

                });


            }
        });


        group = (RadioGroup) findViewById(R.id.rg);
        rg1 = (RadioGroup) findViewById(R.id.rg1);
        rg2 = (RadioGroup) findViewById(R.id.rg2);
        rg3 = (RadioGroup) findViewById(R.id.rg3);


        t1 = (RadioButton) findViewById(R.id.t1);
        t2 = (RadioButton) findViewById(R.id.t2);
        t3 = (RadioButton) findViewById(R.id.t3);
        t4 = (RadioButton) findViewById(R.id.t4);
        t5 = (RadioButton) findViewById(R.id.t5);
        t6 = (RadioButton) findViewById(R.id.t6);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t1.isChecked()) {
                    check();
                    flagselected = false;
                    t1.setChecked(true);
                    t1.setBackgroundColor(Color.RED);
                    time_selected = String.valueOf(t1.getText());
                }
                else{
                    flagselected = true;
                    t1.setChecked(false);
                    t1.setBackgroundColor(Color.WHITE);
                    time_selected = null;

                }
            }
        }); t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t2.isChecked()) {
                    check();
                    flagselected = false;
                    t2.setChecked(true);
                    t2.setBackgroundColor(Color.RED);
                    time_selected = String.valueOf(t2.getText());
                }
                else{
                    flagselected = true;
                    t2.setChecked(false);
                    t2.setBackgroundColor(Color.WHITE);
                    time_selected = null;

                }
            }
        }); t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t3.isChecked()) {
                    check();
                    flagselected = false;
                    t3.setChecked(true);
                    t3.setBackgroundColor(Color.RED);

                    time_selected = String.valueOf(t3.getText());
                }
                else{
                    flagselected = true;
                    t3.setChecked(false);
                    t3.setBackgroundColor(Color.WHITE);
                    time_selected = null;

                }
            }
        }); t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t4.isChecked()) {
                    check();
                    flagselected = false;
                    t4.setChecked(true);
                    t4.setBackgroundColor(Color.RED);

                    time_selected = String.valueOf(t4.getText());
                }
                else{
                    flagselected = true;
                    t4.setChecked(false);
                    t4.setBackgroundColor(Color.WHITE);
                    time_selected = null;

                }
            }
        });
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t5.isChecked()) {
                    check();
                    flagselected = false;
                    t5.setChecked(true);
                    t5.setBackgroundColor(Color.RED);

                    time_selected = String.valueOf(t5.getText());
                }
                else{
                    flagselected = true;
                    t5.setChecked(false);
                    t5.setBackgroundColor(Color.WHITE);
                    time_selected = null;

                }
            }
        });
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t6.isChecked()) {
                    check();
                    flagselected = false;
                    t6.setChecked(true);
                    t6.setBackgroundColor(Color.RED);

                    time_selected = String.valueOf(t6.getText());
                }
                else{
                    flagselected = true;
                    t6.setChecked(false);
                    t6.setBackgroundColor(Color.WHITE);
                    time_selected = null;

                }
            }
        });


        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.datePicker)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {

                date_selected = new SimpleDateFormat("yyyy-MM-dd").format(date);

                //do something
            }
        });


    }

    @Override
    public void onDateSelected(DateTime dateSelected) {

    }

    public void check(){

        if (!flagselected ){
            t1.setChecked(false);
            t1.setBackgroundColor(Color.WHITE);t2.setChecked(false);
            t2.setBackgroundColor(Color.WHITE);t3.setChecked(false);
            t3.setBackgroundColor(Color.WHITE);t4.setChecked(false);
            t4.setBackgroundColor(Color.WHITE);t5.setChecked(false);
            t5.setBackgroundColor(Color.WHITE);t6.setChecked(false);
            t6.setBackgroundColor(Color.WHITE);
        }


    }

}
