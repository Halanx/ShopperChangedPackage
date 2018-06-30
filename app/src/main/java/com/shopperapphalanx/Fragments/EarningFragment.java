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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.shopperapphalanx.Activities.EndlessRecyclerOnScrollListener;
import com.shopperapphalanx.Interfaces.DataInterface;
import com.shopperapphalanx.Interfaces.EarningObject;
import com.shopperapphalanx.Interfaces.EarningRecyclerAdapter;
import com.shopperapphalanx.POJO.BatchInfo;
import com.shopperapphalanx.POJO.earningpagination;
import com.shopperapphalanx.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.shopperapphalanx.GlobalClass.djangoBaseUrl;

public class EarningFragment extends Fragment {

    RecyclerView earningRecycler;
    LinearLayoutManager earningManager;
    RecyclerView.Adapter earningAdapter;
    ProgressBar progressBar;
    int noOfOrders;
    LinearLayout noerning;
    List orderNo = new ArrayList() ;
    List  earn = new ArrayList();
    String token;

    TextView amount_payed,amount_left;

    int pageposition =1;

    Retrofit.Builder builder;
    Retrofit retrofit;
    DataInterface client;
    List<BatchInfo> data;

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
        Volley.newRequestQueue(getActivity()).add(new JsonObjectRequest(Request.Method.GET, djangoBaseUrl+"shoppers/detail/", new Response.Listener<JSONObject>() {
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



        builder = new Retrofit.Builder().baseUrl(djangoBaseUrl).addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        client = retrofit.create(DataInterface.class);



        Log.d("token",token);
        Call<earningpagination> orderCall = client.geteraningdata(token, String.valueOf(1));
        orderCall.enqueue(new Callback<earningpagination>() {
            @Override
            public void onResponse(Call<earningpagination> call, retrofit2.Response<earningpagination> response) {

                noOfOrders = response.body().getResults().size();
                data = response.body().getResults();
                if (noOfOrders > 0) {
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        progressBar.setVisibility(View.GONE);
                        orderNo.add(data.get(i).getId());
                        earn.add(data.get(i).getEarnings());
                    }
                    earningRecycler = (RecyclerView) v.findViewById(R.id.earning_recycler);
                    earningManager = new LinearLayoutManager(getContext());
                    earningAdapter = new EarningRecyclerAdapter(data);
                    earningRecycler.setLayoutManager(earningManager);
                    earningRecycler.setAdapter(earningAdapter);
                    earningRecycler.addOnScrollListener(new EndlessRecyclerOnScrollListener(earningManager) {
                        @Override
                        public void onLoadMore(int current_page) {

                            Log.d("pagination", String.valueOf(pageposition));
                            Call<earningpagination> orderCall = client.geteraningdata(token, String.valueOf(current_page));
                            orderCall.enqueue(new Callback<earningpagination>() {
                                @Override
                                public void onResponse(Call<earningpagination> call, retrofit2.Response<earningpagination> response) {

                                    if (!String.valueOf(response.body()).equals("null")){

                                        List<BatchInfo> allOrdersList = response.body().getResults();


                                        for (int i = 0;i<allOrdersList.size() ; i++) {

                                            data.add(allOrdersList.get(i));
                                            earningAdapter.notifyItemRangeChanged(earningAdapter.getItemCount(),allOrdersList.size()-1);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<earningpagination> call, Throwable t)
                                {
//                            no_internet.setVisibility(View.VISIBLE);

                                }
                            });


                        }
                    });

                }
                else{
                    progressBar.setVisibility(View.GONE);
                    noerning.setVisibility(View.VISIBLE);

                }
           }

            @Override
            public void onFailure(Call<earningpagination> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });



        return v;
    }

}

