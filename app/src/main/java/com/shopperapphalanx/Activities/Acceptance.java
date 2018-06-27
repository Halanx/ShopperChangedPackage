package com.shopperapphalanx.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shopperapphalanx.R;
import com.shopperapphalanx.app.Config;
import com.skyfishjy.library.RippleBackground;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Acceptance extends AppCompatActivity {

    Vibrator v;
    String mob, url;
    String id,json;
    ProgressBar pb;
    ProgressDialog progressDialog;
    LinearLayout batch_cancel;
    RelativeLayout accept_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait a moment");
        progressDialog.setCancelable(false);
        batch_cancel = (LinearLayout) findViewById(R.id.ll_no_completed);

        pb = (ProgressBar) findViewById(R.id.pb);
        accept_layout = (RelativeLayout) findViewById(R.id.acceptlayout);

        final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 1000, 1000};
        v.vibrate(pattern, 0);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        json = pref.getString("json_data", null);

        id = pref.getString("batch_id", null);

        Log.d("recievedate",id + " , "+ json);


        mob = getSharedPreferences("Login", Context.MODE_PRIVATE).getString("MobileNumber", null);
        url = "https://api.halanx.com/batch/" + id + "/";


        RelativeLayout proceed = (RelativeLayout) findViewById(R.id.btProceed_dialogue);
        RelativeLayout cancel = (RelativeLayout) findViewById(R.id.btCancel_dialogue);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();

                JSONObject objAccept = new JSONObject();
                try {
                    objAccept.put("PermanentAvailable", true);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("object", String.valueOf(objAccept));
                try {
                    Volley.newRequestQueue(Acceptance.this).add(new JsonObjectRequest(Request.Method.PATCH, url, objAccept, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("response", String.valueOf(response));
                            String batchId = getSharedPreferences("Batch", Context.MODE_PRIVATE).getString("batchId", null);
                            getSharedPreferences("Batch", Context.MODE_PRIVATE).edit().putBoolean("isBusy", true).apply();
                            v.cancel();
                            Intent intent = new Intent(Acceptance.this, OnWayStoreActivity.class);
                            intent.putExtra("batch", batchId);
                            progressDialog.dismiss();
                            startActivity(intent);
                            finish();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/json");
                            params.put("Authorization", getApplicationContext().getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token", null));
                            return params;
                        }

                    });

                    String mobile = getSharedPreferences("Login", Context.MODE_PRIVATE).getString("MobileNumber", null);

                    String url = "https://api.halanx.com/shoppers/detail/";

                    // Request a string response from the provided URL.
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("Busy", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Volley.newRequestQueue(getApplicationContext()).add(new JsonObjectRequest(Request.Method.PATCH, url, obj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("TAG", "ONLINE");

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/json");
                            params.put("Authorization", getApplicationContext().getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token", null));
                            return params;
                        }

                    });
                }
                catch(Exception e){
                    batch_cancel.setVisibility(View.VISIBLE);
                    accept_layout.setVisibility(View.GONE);
                    v.cancel();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                JSONObject objReject = new JSONObject();
                try {
                    objReject.put("TemporaryAvailable", false);
                    objReject.put("TemporaryShopper", null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
try {
    Volley.newRequestQueue(Acceptance.this).add(new JsonObjectRequest(Request.Method.PATCH, url, objReject, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            progressDialog.dismiss();
            v.cancel();
            finish();
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            progressDialog.dismiss();
        }
    }) {
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Content-Type", "application/json");
            params.put("Authorization", getApplicationContext().getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token", null));
            return params;
        }

    });
}

                catch(Exception e){
                    batch_cancel.setVisibility(View.VISIBLE);
                    accept_layout.setVisibility(View.GONE);
                    finish();
                    v.cancel();
                }


            }
        });

    }
}
