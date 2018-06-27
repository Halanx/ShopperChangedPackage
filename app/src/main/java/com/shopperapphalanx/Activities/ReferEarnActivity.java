package com.shopperapphalanx.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shopperapphalanx.R;


public class ReferEarnActivity extends AppCompatActivity {

    ImageButton ivShare;
    TextView tvShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_earn);


        tvShare = (TextView) findViewById(R.id.tv_share);
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareIt();
            }
        });

    }

    private void shareIt() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

        /*
        Set a MIME type for the content you're sharing. This will determine which applications
        the chooser list presents to your users. Plain text, HTML, images and videos are among
        the common types to ivShare. The following Java code demonstrates sending plain text.
         */
        sharingIntent.setType("text/plain");

        /*
       You can pass various elements of your sharing content to the send Intent, including subject,
       text / media content, and addresses to copy to in the case of mobile sharing. This Java code
       builds a string variable to hold the body of the text content to ivShare:
   */

        String shareBody = "Earn money on your own schedule. Shop and deliver nearby, serving people for their smiles. Download the app now: "+"https://play.google.com/store/apps/details?id=com.shopperapphalanx";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Halanx Shopper");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

        /*
        You can't ivShare the same content in a text message or tweet that you could send using
        mobile. For this reason it's best to keep your sharing content as general as possible, so
        that the function will be as effective for Twitter and Facebook as it is for Gmail and mobile.
         */
    }

}
