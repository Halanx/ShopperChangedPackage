package com.shopperapphalanx.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shopperapphalanx.Interfaces.EarningObject;
import com.shopperapphalanx.Interfaces.EarningRecyclerAdapter;
import com.shopperapphalanx.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shopperapphalanx.GlobalClass.djangoBaseUrl;

public class EarningFragment extends Fragment {

    RecyclerView earningRecycler;
    RecyclerView.LayoutManager earningManager;
    RecyclerView.Adapter earningAdapter;
    ProgressBar progressBar;
    int noOfOrders;
    LinearLayout noerning;
    List orderNo = new ArrayList() ;
    List  earn = new ArrayList();
    String token;

    TextView amount_payed,amount_left;

    public EarningFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_earning, container,false);
        progressBar = (ProgressBar) v.findViewById(R.id.pb);
        noerning = (LinearLayout) v.findViewById(R.id.ll_no_completed);
        token = getActivity().getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token",null);
        Log.d("token",token);
        amount_left = (TextView) v.findViewById(R.id.amount_left);
        amount_payed = (TextView) v.findViewById(R.id.amount_payed);
        Volley.newRequestQueue(getActivity()).add(new JsonObjectRequest(Request.Method.GET, djangoBaseUrl+"shoppers/detail", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    amount_left.setText(response.getString("due_amount"));
                    amount_payed.setText(response.getString("paid_amount"));
               } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", token);
                return params;
            }

        }.setRetryPolicy(new RetryPolicy() {
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
        }));

        Volley.newRequestQueue(getActivity()).add(new JsonArrayRequest(Request.Method.GET, djangoBaseUrl+"shoppers/deliveredbatches/", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response", String.valueOf(response));
                noOfOrders = response.length();
                if (noOfOrders > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            orderNo.add(response.getJSONObject(i).getInt("batch"));
                            earn.add(response.getJSONObject(i).getString("Earnings"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    earningRecycler = (RecyclerView) v.findViewById(R.id.earning_recycler);
                    earningManager = new LinearLayoutManager(getContext());
                    earningAdapter = new EarningRecyclerAdapter(getEarningData());
                    earningRecycler.setLayoutManager(earningManager);
                    earningRecycler.setAdapter(earningAdapter);
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    noerning.setVisibility(View.VISIBLE);

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", token);
                return params;
            }
        }.setRetryPolicy(new RetryPolicy() {
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
        }));




        return v;
    }

    public ArrayList<EarningObject> getEarningData(){

        ArrayList<EarningObject> results = new ArrayList<>();

        for(int i = 0; i < noOfOrders ;i++){
            EarningObject dataObject = new EarningObject(""+(i+1), orderNo.get(i), earn.get(i));
            results.add(i,dataObject);
        }
        return results;
    }
}

