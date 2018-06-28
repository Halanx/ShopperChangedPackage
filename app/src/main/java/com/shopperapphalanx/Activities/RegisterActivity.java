package com.shopperapphalanx.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shopperapphalanx.POJO.Resp;
import com.shopperapphalanx.R;
import com.shopperapphalanx.app.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by samarthgupta on 13/02/17.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputfname, inputlname,
            inputmno, inputCity, inputicode;

    private Button btnRegister, btnVerify;
    private ProgressBar progressRegister;

    String email, password, firstName, lastName, mno, city, icode, random;
    Resp resp;

       String regId;

    String phpURL = "https://api.halanx.com";
    String djangoURL = "https://api.halanx.com";
    String url;
    Dialog dialog;
    ProgressBar pb,pbresend;
    Button btnOtpSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        btnRegister = (Button) findViewById(R.id.btn_register);
        btnVerify = (Button) findViewById(R.id.btn_verify);
        inputEmail = (EditText) findViewById(R.id.tv_email);
        inputPassword = (EditText) findViewById(R.id.tv_password);
        progressRegister = (ProgressBar) findViewById(R.id.progressBar_register);
        inputCity = (EditText) findViewById(R.id.tv_city);
        inputfname = (EditText) findViewById(R.id.tv_firstName);
        inputlname = (EditText) findViewById(R.id.tv_lastName);
        inputmno = (EditText) findViewById(R.id.tv_mobile);
        inputicode = (EditText) findViewById(R.id.tv_inviteCode);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        btnRegister.setVisibility(View.GONE);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                firstName = inputfname.getText().toString().trim();
                lastName = inputlname.getText().toString().trim();
                mno = inputmno.getText().toString().trim();
                city = inputCity.getText().toString().trim();
                icode = inputicode.getText().toString().trim();


                //  CHECKING ALL EDIT TEXT FIELDS
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
                    Toast.makeText(getApplicationContext(), "Enter First and Last name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(mno)) {
                    Toast.makeText(getApplicationContext(), "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mno.length() != 10) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid mobile number",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(city)) {
                    Toast.makeText(getApplicationContext(), "Enter your city", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Internet available
                {


                    dialog = new Dialog(RegisterActivity.this);
                    dialog.setContentView(R.layout.activity_verify);
                    dialog.setTitle("OTP has been sent to" + mno);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

                    JSONObject json = new JSONObject();
                    try {
                        json.put("FirstName",firstName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Volley.newRequestQueue(getApplicationContext()).add(new JsonObjectRequest(Request.Method.POST, djangoURL+"users/getotp/" + mno+"/", json, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("otp_response", String.valueOf(response));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }));

                    final TextView tvResendOtp = (TextView) dialog.findViewById(R.id.resend);

                    final EditText otp = (EditText) dialog.findViewById(R.id.enterOTP);
                    btnOtpSubmit = (Button) dialog.findViewById(R.id.btnOTPsubmit);
                    TextView tvNumber = (TextView) dialog.findViewById(R.id.dialogue_number);
                    pb = (ProgressBar) dialog.findViewById(R.id.pb);
                    pbresend = (ProgressBar) dialog.findViewById(R.id.pbresend);


                    tvNumber.setText(mno);
                    btnOtpSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!String.valueOf(otp.getText()).equals("")) {
                                btnOtpSubmit.setVisibility(View.GONE);
                                pb.setVisibility(View.VISIBLE);
                                registration(otp.getText().toString());
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Please enter the OTP",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    tvResendOtp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            pbresend.setVisibility(View.VISIBLE);
                            tvResendOtp.setVisibility(View.GONE);

                            JSONObject json = new JSONObject();
                            try {
                                json.put("FirstName",firstName);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Volley.newRequestQueue(getApplicationContext()).add(new JsonObjectRequest(Request.Method.POST, djangoURL+"users/getotp/" + mno+"/", new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("otp_response", String.valueOf(response));
                                    pbresend.setVisibility(View.GONE);
                                    tvResendOtp.setVisibility(View.VISIBLE);

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    tvResendOtp.setVisibility(View.VISIBLE);
                                    pbresend.setVisibility(View.GONE);

                                }
                            }));


//                            if (otp.getText().toString().equals(randomRes)) {
//                                Toast.makeText(RegisterActivity.this, "User Verified", Toast.LENGTH_LONG).show();
//                                dialog.dismiss();
//                                registration();
//                            } else {
//                                Toast.makeText(RegisterActivity.this, "Incorrect OTP entered", Toast.LENGTH_LONG).show();
//                                return;
//                            }

                        }
                    });


                }


            }
        });


    }


    public void registration(String text) {

        //Put cart on server after account is created

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", "c" + mno);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("first_name", firstName);
            jsonObject.put("last_name", lastName);
            jsonObject.put("phone_no", mno);
            jsonObject.put("otp",Integer.parseInt(text));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            //Save user details in sharedPreferences
            Log.d("user", "done");
            Volley.newRequestQueue(RegisterActivity.this).add(new JsonObjectRequest(Request.Method.POST, "https://api.halanx.com/users/", jsonObject, new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response1) {

                    Log.i("TAG", String.valueOf(response1));

                    try {
                        getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).edit().putString("token", "token " + response1.getString("key")).commit();
                        Volley.newRequestQueue(RegisterActivity.this).add(new JsonObjectRequest(Request.Method.GET, djangoURL+"users/detail/", jsonObject, new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("data", String.valueOf(response));

                                try {
                                    getSharedPreferences("Login", Context.MODE_PRIVATE).edit().
                                            putString("firstname", response.getJSONObject("user").getString("first_name")).
                                            putString("lastname", response.getJSONObject("user").getString("last_name")).
                                            putString("UserInfo", String.valueOf(response)).putString("phone_no", mno).
                                            putBoolean("first_login", true).
                                            putBoolean("Loginned", true).apply();
                                    getSharedPreferences("status", Context.MODE_PRIVATE).edit().
                                            putBoolean("first_login", true).apply();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                btnOtpSubmit.setVisibility(View.VISIBLE);
                                pb.setVisibility(View.GONE);

                                Log.i("TAG", String.valueOf(response));
                                Log.i("TAG", "Info" + getSharedPreferences("Login", Context.MODE_PRIVATE).getString("UserInfo", null));
                                startActivity(new Intent(RegisterActivity.this, HomeActivity.class));

                                finish();


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                btnOtpSubmit.setVisibility(View.VISIBLE);
                                pb.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Use Already exist!", Toast.LENGTH_SHORT).show();

                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                try {
                                    params.put("Content-Type", "application/json");
                                    params.put("Authorization", "token " + response1.getString("key"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                return params;
                            }

                        });

                    } catch (JSONException e) {
                        btnOtpSubmit.setVisibility(View.VISIBLE);
                        pb.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(), "Use Already exist!", Toast.LENGTH_SHORT).show();

                    }

                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error", String.valueOf(error));
                    Toast.makeText(getApplicationContext(), "Use Already exist!", Toast.LENGTH_SHORT).show();

                    btnOtpSubmit.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);

                }
            }));
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
            btnOtpSubmit.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);



        }

    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }


}