package com.shopperapphalanx.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.shopperapphalanx.Activities.Acceptance;
import com.shopperapphalanx.Activities.HomeActivity;
import com.shopperapphalanx.R;
import com.shopperapphalanx.app.Config;
import com.shopperapphalanx.util.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by Nishant on 15/07/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    private android.support.v4.app.NotificationCompat.Style bigPictureStyle;
    long[] pattern = {0, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500, 2000, 500};
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                getSharedPreferences("BatchData",Context.MODE_PRIVATE).edit().putString("Batch",remoteMessage.getData().toString()).apply();
                Log.d("jsondata", String.valueOf(json));
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
//            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//            pushNotification.putExtra("message", message);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());
        String title=null;

        try {
            title = json.getString("BatchId");


            SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("batch_id",title);
            editor.commit();
            Log.e(TAG, "title: " + title);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message

                Log.d("data", String.valueOf(json));
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                //     pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                startActivity(new Intent(MyFirebaseMessagingService.this, Acceptance.class));
                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();

            }
            else {
                Intent resultIntent = new Intent(this, HomeActivity.class);

                resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent piResult = PendingIntent.getActivity(this,
                        (int) Calendar.getInstance().getTimeInMillis(), resultIntent, 0);

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);

                Intent resultIntenta = new Intent(this, Acceptance.class);

                resultIntenta.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent piResulta = PendingIntent.getActivity(this,
                        (int) Calendar.getInstance().getTimeInMillis(), resultIntenta, 0);


// Assign big picture notification
                NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();

                bigPictureStyle.bigPicture(
                        BitmapFactory.decodeResource(getResources(),
                                R.drawable.logoc)).build();

                NotificationCompat.Builder builder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.logochange)
                                .setContentTitle("Halanx")
                                .setContentText("Hey! Your BatchInfo is ready")
                                .setSound(RingtoneManager.getValidRingtoneUri(getApplicationContext()))
                                .setStyle(bigPictureStyle)
                                .setContentIntent(piResulta)
                                .setVibrate(pattern)
                                .setOngoing(true)
                                .setAutoCancel(true);
//set intents and pending intents to call activity on click of "show activity" action button of notification

// Gets an instance of the NotificationManager service
                NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                builder.build().flags |= builder.build().FLAG_INSISTENT;

//to post your notification to the notification bar
                notificationManager.notify(0, builder.build());



//                Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
//                resultIntent.putExtra("message", message);
//
//                // check for image attachment
//                if (TextUtils.isEmpty("data")) {
//                    showNotificationMessage(getApplicationContext(), title, message, resultIntent);
//                } else {
//                    // image is present, show notification with image
//                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, resultIntent);
//                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage("data", message, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent);
    }
}