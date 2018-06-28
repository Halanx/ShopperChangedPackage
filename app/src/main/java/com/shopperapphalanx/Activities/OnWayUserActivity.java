package com.shopperapphalanx.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;
import com.shopperapphalanx.Adapters.BatchUserAdapter;
import com.shopperapphalanx.POJO.BatchInfo;
import com.shopperapphalanx.R;
import com.shopperapphalanx.app.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.shopperapphalanx.GlobalClass.djangoBaseUrl;

public class OnWayUserActivity extends AppCompatActivity {


    RecyclerView rv;
    CardView cvDrop;
    BatchInfo batch;
    int i;
    ProgressDialog pd;
    String url;
    String id;
    ProgressBar progressBar;
    TextView batchno;

    LinearLayout ll_detail;
    TextView order;
    TextView cod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_user);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        rv = (RecyclerView) findViewById(R.id.rv_batch_user);
        batchno = (TextView) findViewById(R.id.batchno);
        cvDrop = (CardView) findViewById(R.id.cv_drop);
        ll_detail = findViewById(R.id.detail);
        order = findViewById(R.id.orderno);
        cod = findViewById(R.id.cod);

        id = getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE).getString("batch_id", "1");
        url = djangoBaseUrl + "batch/" + id + "/";
        Log.d("url",url);


        Volley.newRequestQueue(this).add(new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("RES", String.valueOf(response));

                progressBar.setVisibility(View.GONE);
                batchno.setText(id);
                batch = new GsonBuilder().create().fromJson(String.valueOf(response), BatchInfo.class);

                BatchUserAdapter adapter = new BatchUserAdapter(batch, OnWayUserActivity.this);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(OnWayUserActivity.this);
                rv.setLayoutManager(manager);
                rv.setAdapter(adapter);
                rv.setHasFixedSize(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", getApplicationContext().getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token", null));
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

        cvDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                pd = new ProgressDialog(OnWayUserActivity.this);
                pd.setTitle("Please wait");
                pd.show();

                final String token =  getApplicationContext().getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token", null);
                Log.d("token",token);
                JSONObject obj = new JSONObject();
                try {
                    obj.put("IsDelivered", true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Volley.newRequestQueue(OnWayUserActivity.this).add(new JsonObjectRequest(Request.Method.PATCH, url, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", String.valueOf(response));
                        String url = "https://api.halanx.com/shoppers/detail/";
                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("Busy", false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Volley.newRequestQueue(OnWayUserActivity.this).add(new JsonObjectRequest(Request.Method.PATCH, url, obj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("response", String.valueOf(response));

                                getSharedPreferences("Batch", Context.MODE_PRIVATE).edit().putBoolean("isBusy", false).apply();
                                startActivity(new Intent(OnWayUserActivity.this, HomeActivity.class));
                                pd.dismiss();
                                finish();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(OnWayUserActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Content-Type", "application/json");
                                params.put("Authorization",token);
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

                            }
                        });



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OnWayUserActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        params.put("Authorization",token);
                        return params;
                    }

                });



            }
        });
    }
}
