package com.shopperapphalanx.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shopperapphalanx.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.shopperapphalanx.GlobalClass.djangoBaseUrl;


public class SplashActivity extends AppCompatActivity {


    public static Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Volley.newRequestQueue(getApplicationContext()).add(new JsonObjectRequest(Request.Method.GET, djangoBaseUrl+"version/2/", new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String androidOS = String.valueOf((getPackageManager().getPackageInfo(getPackageName(), 0).versionCode));
                    String playstoreversion = response.getString("version");
                    Log.d("repsonse",androidOS+","+response.getInt("version"));

                    if (Integer.parseInt(androidOS.trim())<(Integer.parseInt(playstoreversion.trim())))
                    {
                        flag = true;
                        Log.d("done","doneabc");
                    }


                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, SignInActivity.class)); //SignInActivity
                finish();
            }
        }, 2000);


//        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://ec2-34-208-181-152.us-west-2.compute.amazonaws.com/").addConverterFactory(GsonConverterFactory.create());
//        Retrofit retrofit = builder.build();
//        DataInterface client = retrofit.create(DataInterface.class);
//
//        Call<List<CartItem>> call = client.getCartItems();
//        call.enqueue(new Callback<List<CartItem>>() {
//            @Override
//            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
//
//                List<CartItem> list = response.body();
//                Collections.sort(list, new Comparator<CartItem>() {
//                    @Override
//                    public int compare(CartItem cart1, CartItem cart2) {
//                        return new CompareToBuilder().append(cart1.getItem().getStoreId(),cart2.getItem().getStoreId())
//                                .append(cart1.getCartPhoneNo(),cart2.getCartPhoneNo())
//                                .toComparison();
//                    }
//                });
//
//                Log.i("TAG123", String.valueOf(list.get(0).getCartPhoneNo()));
//                Log.i("TAG123", String.valueOf(list.get(list.size()-1).getItem().getStoreId()));
//
//            }
//
//            @Override
//            public void onFailure(Call<List<CartItem>> call, Throwable t) {
//
//            }
//        });

    }
}
