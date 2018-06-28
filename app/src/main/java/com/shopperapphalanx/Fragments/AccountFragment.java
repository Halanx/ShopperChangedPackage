package com.shopperapphalanx.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;
import com.shopperapphalanx.Activities.AboutActivity;
import com.shopperapphalanx.Activities.DocumentsActivity;
import com.shopperapphalanx.Activities.HelpActivity;
import com.shopperapphalanx.Activities.ReferEarnActivity;
import com.shopperapphalanx.Activities.Schedule;
import com.shopperapphalanx.Activities.SignInActivity;
import com.shopperapphalanx.POJO.ShopperInfo;
import com.shopperapphalanx.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.shopperapphalanx.GlobalClass.djangoBaseUrl;


public class AccountFragment extends Fragment implements View.OnClickListener {

    CardView cvSignOut, cv_help, cv_promo, cv_about, cv_doc, cv_sched;
    TextView tvName,tvVehicle;
    String token;
    CircleImageView profile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        String shopperInfo = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE).getString("UserInfo", null);
        ShopperInfo shopper = new GsonBuilder().create().fromJson(shopperInfo, ShopperInfo.class);



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_original, container, false);



        token = getActivity().getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token",null);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvVehicle = (TextView) view.findViewById(R.id.tv_vehicle);
        profile = view.findViewById(R.id.profile);


        Volley.newRequestQueue(getActivity()).add(new JsonObjectRequest(Request.Method.GET, djangoBaseUrl+"shoppers/detail", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    tvName.setText(response.getJSONObject("user").getString("first_name"));
                    tvVehicle.setText(response.getString("vehicle"));
//                    Picasso.with(getActivity()).load().into(profile);
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

        });


        cv_promo = (CardView) view.findViewById(R.id.cvPromotion);
        cv_promo.setOnClickListener(this);

        cv_help = (CardView) view.findViewById(R.id.cvHelp);
        cv_help.setOnClickListener(this);

        cv_doc = (CardView) view.findViewById(R.id.cvDocs);
        cv_doc.setOnClickListener(this);

        cv_sched = (CardView) view.findViewById(R.id.cvSchedule);
        cv_sched.setOnClickListener(this);

        cvSignOut = (CardView) view.findViewById(R.id.cvSignOut);
        cvSignOut.setOnClickListener(this);

        cv_about = (CardView) view.findViewById(R.id.cvAbout);
        cv_about.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cvSignOut:
                getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE).edit().
                        putBoolean("Loginned", false).remove("MobileNumber")
                        .remove("UserInfo").apply();

                startActivity(new Intent(getActivity(), SignInActivity.class));
                getActivity().finish();

                break;
            case R.id.cvAbout:
                startActivity(new Intent(getContext(), AboutActivity.class));
                break;

            case R.id.cvHelp:
                startActivity(new Intent(getActivity(), HelpActivity.class));
                break;
            case R.id.cvDocs:
                startActivity(new Intent(getContext(), DocumentsActivity.class));
                break;
            case R.id.cvPromotion:
                startActivity(new Intent(getActivity(), ReferEarnActivity.class));
                break;
            case R.id.cvSchedule:
                startActivity(new Intent(getContext(), Schedule.class));
                break;

            case R.id.cvSettings:
                break;


        }
    }
}
