package com.shopperapphalanx.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.shopperapphalanx.Fragments.AccountFragment;
import com.shopperapphalanx.Fragments.EarningFragment;
import com.shopperapphalanx.Fragments.HomeFragment;
import com.shopperapphalanx.Fragments.RatingFragment;
import com.shopperapphalanx.R;
import com.shopperapphalanx.app.Config;
import com.shopperapphalanx.util.NotificationUtils;

public class HomeActivity extends AppCompatActivity {
    HomeFragment homeFragment;
    EarningFragment earningFragment;
    RatingFragment ratingFragment;
    AccountFragment accountFragment;
    String data;
    AlertDialog.Builder alertdialog;

    private BroadcastReceiver mRegistrationBroadcastReceiver;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        homeFragment = new HomeFragment();
        earningFragment = new EarningFragment();
        ratingFragment = new RatingFragment();
        accountFragment = new AccountFragment();


        if(SplashActivity.flag){
            alertdialog = new AlertDialog.Builder(this);
            alertdialog.setTitle("Update")
                    .setMessage("Update your app to continue with all new features").setCancelable(false)
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.shopperapphalanx"));
                            startActivity(intent);
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

        }
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragmentManager.beginTransaction().replace(R.id.content, homeFragment, homeFragment.getTag()).commit();
                        return true;

                    case R.id.navigation_earnings:
                        fragmentManager.beginTransaction().replace(R.id.content, earningFragment, earningFragment.getTag()).commit();
                        return true;

                    case R.id.navigation_rating:
                        fragmentManager.beginTransaction().replace(R.id.content, ratingFragment, ratingFragment.getTag()).commit();
                        return true;

                    case R.id.navigation_account:
                        // fragmentManager.beginTransaction().replace(R.id.content, accountFragment).commit();
                        fragmentManager.beginTransaction().replace(R.id.content, accountFragment, accountFragment.getTag()).commit();

                        return true;

                }
                return false;
            }
        };





        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getTag();
        navigation.setSelectedItemId(R.id.navigation_home);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                }
            }
        };

        displayFirebaseRegId();
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e("firebase", "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)){
            Log.d("done",regId);
        }
        else
            Log.d("sone","Firebase Reg Id is not received yet!");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
