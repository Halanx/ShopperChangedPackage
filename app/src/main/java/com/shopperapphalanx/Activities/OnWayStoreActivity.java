package com.shopperapphalanx.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;
import com.shopperapphalanx.Adapters.BatchesStoreAdapter;
import com.shopperapphalanx.POJO.BatchInfo;
import com.shopperapphalanx.R;
import com.shopperapphalanx.app.Config;

import java.util.HashMap;
import java.util.Map;

public class OnWayStoreActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv;
    TextView pickup;
    ProgressBar progressBar;
    TextView batchno;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_store);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        batchno = (TextView) findViewById(R.id.batchno);

        id = getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE).getString("batch_id","0");
        pickup = (TextView) findViewById(R.id.pickup);
        rv = (RecyclerView) findViewById(R.id.rv_batches);
        Log.d("getids",id);

        String url = "https://api.halanx.com/batch/" + id+ "/";
        Log.d("stringvalue",url);
        Volley.newRequestQueue(this).add(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("string",response);
                progressBar.setVisibility(View.GONE);
                batchno.setText(String.valueOf(id));
                BatchInfo batch = new GsonBuilder().create().fromJson(response, BatchInfo.class);
                BatchesStoreAdapter adapter = new BatchesStoreAdapter(batch, OnWayStoreActivity.this);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(OnWayStoreActivity.this);
                rv.setLayoutManager(manager);
                rv.setAdapter(adapter);
                rv.setHasFixedSize(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", getApplicationContext().getSharedPreferences("Tokenkey",Context.MODE_PRIVATE).getString("token",null));
                return params;
            }

        }).setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 100000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 100000;
            }

            @Override
            public void retry(VolleyError volleyError) throws VolleyError {

                progressBar.setVisibility(View.GONE);
            }
        });

        pickup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        startActivity(new Intent(OnWayStoreActivity.this,OnWayUserActivity.class));
        finish();
    }
}
