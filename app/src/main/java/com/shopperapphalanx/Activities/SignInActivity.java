package com.shopperapphalanx.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.shopperapphalanx.R;
import com.shopperapphalanx.app.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.shopperapphalanx.GlobalClass.djangoBaseUrl;

/**
 * Created by samarthgupta on 12/02/17.
 */


public class SignInActivity extends AppCompatActivity {

    private EditText inputMobile, inputPassword;
    private ProgressBar progressBar;
    private TextView btnRegister;
    private Button btnLogin;
    SharedPreferences sharedPreferences;
    String regId = null;
    String mobile;
    String password;
    TextView tv_forgot;

    String mobilenumber;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        Boolean loginStatus = sharedPreferences.getBoolean("Loginned", false);
        if (loginStatus) {
            startActivity(new Intent(SignInActivity.this, HomeActivity.class));
            finish();
        }


        //Giving null point exception when app opened for the first time
        if (getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0).getString("redId", null) != null) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
            regId = pref.getString("regId", null);
            Log.d("regid", regId);
        }

        setContentView(R.layout.activity_signin);


        inputMobile = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        btnRegister = (TextView) findViewById(R.id.signUp);


        tv_forgot = findViewById(R.id.forgotpassword);

        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               {

                   mobilenumber = inputMobile.getText().toString();

                    if ((mobilenumber.length() > 10) || (mobilenumber.length() < 10)) {

                        Toast.makeText(getApplicationContext(), "Please enter the correct number", Toast.LENGTH_SHORT).show();
                    } else {


                        final String mobile = mobilenumber.trim();

                        final Dialog dialog = new Dialog(SignInActivity.this);
                        dialog.setContentView(R.layout.activity_verify);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        Window window = dialog.getWindow();
                        window.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

                        JSONObject json = new JSONObject();
                        try {
                            json.put("FirstName", "User");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Volley.newRequestQueue(getApplicationContext()).add(new JsonObjectRequest(Request.Method.POST, djangoBaseUrl + "users/getotp/" + mobile + "/", json, new Response.Listener<JSONObject>() {
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
                        final Button btnOtpSubmit = (Button) dialog.findViewById(R.id.btnOTPsubmit);
                        TextView tvNumber = (TextView) dialog.findViewById(R.id.dialogue_number);
                        final ProgressBar pb = (ProgressBar) dialog.findViewById(R.id.pb);
                        final ProgressBar pbresend = (ProgressBar) dialog.findViewById(R.id.pbresend);


                        tvNumber.setText(mobile);
                        btnOtpSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                btnOtpSubmit.setVisibility(View.GONE);
                                pb.setVisibility(View.VISIBLE);

                                String url = djangoBaseUrl + "users/loginotp/";
                                JSONObject json = new JSONObject();
                                try {
                                    json.put("username", "s" + mobile.trim());
                                    json.put("password", Integer.parseInt(String.valueOf(otp.getText()).trim()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Volley.newRequestQueue(getApplicationContext()).add(new JsonObjectRequest(Request.Method.POST, url, json, new com.android.volley.Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        String token = null;

                                        Log.d("response", String.valueOf(response));
                                        try {
                                            token = response.getString("key");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        {
                                            dialog.dismiss();
                                            final Dialog dialog1 = new Dialog(SignInActivity.this);
                                            dialog1.setContentView(R.layout.forgotpasswordlayout);
                                            dialog1.setTitle("Set Your New Password");
                                            dialog1.setCanceledOnTouchOutside(false);
                                            dialog1.show();
                                            Window window = dialog1.getWindow();
                                            window.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

                                            final EditText new_password = (EditText) dialog1.findViewById(R.id.password);
                                            final ProgressBar pbar = dialog1.findViewById(R.id.pb);
                                            final Button done = (Button) dialog1.findViewById(R.id.button_done);

                                            final String finalToken = token;
                                            done.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    pbar.setVisibility(View.VISIBLE);
                                                    done.setVisibility(View.GONE);

                                                    String url = djangoBaseUrl + "rest-auth/password/change/";
                                                    JSONObject json = new JSONObject();

                                                    try {
                                                        json.put("new_password1", String.valueOf(new_password.getText()).trim());
                                                        json.put("new_password2", String.valueOf(new_password.getText()).trim());

                                                        Volley.newRequestQueue(getApplicationContext()).add(new JsonObjectRequest(Request.Method.POST, url, json, new com.android.volley.Response.Listener<JSONObject>() {
                                                            @Override
                                                            public void onResponse(JSONObject response) {
                                                                Toast.makeText(getApplicationContext(), "Password successfully changed", Toast.LENGTH_SHORT).show();
                                                                dialog1.dismiss();

                                                                pbar.setVisibility(View.GONE);
                                                                done.setVisibility(View.VISIBLE);


                                                            }
                                                        }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Log.d("ERROR", "error");
                                                                pbar.setVisibility(View.GONE);
                                                                done.setVisibility(View.VISIBLE);

                                                            }
                                                        }) {
                                                            @Override
                                                            public Map<String, String> getHeaders() throws AuthFailureError {
                                                                Map<String, String> params = new HashMap<String, String>();
                                                                params.put("Content-Type", "application/json");
                                                                params.put("Authorization", "token " + finalToken);
                                                                return params;
                                                            }

                                                        });
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                        pbar.setVisibility(View.GONE);
                                                        done.setVisibility(View.VISIBLE);
                                                        Toast.makeText(getApplicationContext(), "Please register first", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });
                                        }
                                    }
                                }, new com.android.volley.Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("error", String.valueOf(error));
                                        pb.setVisibility(View.GONE);
                                        btnOtpSubmit.setVisibility(View.VISIBLE);
                                        Toast.makeText(getApplicationContext(), "Please register first", Toast.LENGTH_SHORT).show();


                                    }
                                }));

                            }
                        });
                        tvResendOtp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                pbresend.setVisibility(View.VISIBLE);
                                tvResendOtp.setVisibility(View.GONE);


                                JSONObject json = new JSONObject();
                                try {
                                    json.put("FirstName", "User");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Volley.newRequestQueue(getApplicationContext()).add(new JsonObjectRequest(Request.Method.POST, djangoBaseUrl + "users/getotp/" + mobile + "/", json, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("otp_response", String.valueOf(response));
                                        tvResendOtp.setVisibility(View.VISIBLE);
                                        pbresend.setVisibility(View.GONE);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        tvResendOtp.setVisibility(View.VISIBLE);
                                        pbresend.setVisibility(View.GONE);

                                    }
                                }));
                            }

                        });
                    }
                }
            }
        });








        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);
                mobile = inputMobile.getText().toString().trim();
                password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(getApplicationContext(), "Enter mobile address", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setVisibility(View.VISIBLE);


                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setVisibility(View.VISIBLE);

                    return;
                }



                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username","s"+ mobile);
                    jsonObject.put("password",password);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    Volley.newRequestQueue(SignInActivity.this).add(new JsonObjectRequest(Request.Method.POST, djangoBaseUrl+"rest-auth/login/", jsonObject, new com.android.volley.Response.Listener<JSONObject>() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("data", String.valueOf(response));
                            final String token;
                            try {
                                token = response.getString("key");
                                Log.d("key", token);

                                getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).edit().putString("token", "token " + token).commit();
                                Log.d("token_key", getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token", null));
                                Volley.newRequestQueue(SignInActivity.this).add(new JsonObjectRequest(Request.Method.GET, djangoBaseUrl+"shoppers/detail/", jsonObject, new com.android.volley.Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("data", String.valueOf(response));
                                        getSharedPreferences("Login", Context.MODE_PRIVATE).edit().
                                                putString("UserInfo", String.valueOf(response)).putString("MobileNumber", mobile).
                                                putBoolean("first_login", true).
                                                putBoolean("Loginned", true).apply();

                                        getSharedPreferences("status", Context.MODE_PRIVATE).edit().
                                                putBoolean("first_login", true).apply();


                                        Log.i("TAG", String.valueOf(response));
                                        Log.i("TAG", "Info" + getSharedPreferences("Login", Context.MODE_PRIVATE).getString("UserInfo", null));
                                        startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                                        progressBar.setVisibility(View.INVISIBLE);
//
                                        finish();


                                    }
                                }, new com.android.volley.Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        btnLogin.setVisibility(View.VISIBLE);
                                    }
                                }) {
                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Content-Type", "application/json");
                                        params.put("Authorization", "token " + token);
                                        return params;
                                    }

                                });


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                btnLogin.setVisibility(View.VISIBLE);
                            }


                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.d("error", String.valueOf(error));
                            Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.INVISIBLE);
                            btnLogin.setVisibility(View.VISIBLE);
                            return;

                        }
                    }
                    ));
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);

                }


            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, RegisterActivity.class));

            }
        });


    }
}