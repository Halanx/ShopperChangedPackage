package com.shopperapphalanx.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shopperapphalanx.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.shopperapphalanx.Activities.GlobalClass.djangoBaseUrl;

public class RatingFragment extends Fragment {

    TextView rating_text;
    String data;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating, container, false);

        rating_text = (TextView) view.findViewById(R.id.rating_text);
        String mobile = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE).getString("MobileNumber", null);

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url =djangoBaseUrl+"shoppers/detail/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            data = json.getString("AvgRating").substring(0,3);
                           rating_text.setText(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Display the first 500 characters of the response string.
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
                params.put("Authorization", getActivity().getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token", null));
                return params;
            }

        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);






        return view;
    }
}


