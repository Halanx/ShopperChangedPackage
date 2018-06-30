package com.shopperapphalanx.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.shopperapphalanx.Interfaces.DataInterface;
import com.shopperapphalanx.POJO.DocsInfo;
import com.shopperapphalanx.R;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.shopperapphalanx.GlobalClass.djangoBaseUrl;

public class DocumentsActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE_ADHAAR = 1;
    static final int REQUEST_IMAGE_CAPTURE_VLICENSE = 2;
    static final int REQUEST_IMAGE_CAPTURE_DLICENSE = 3;
    ImageView btAdhaar, btVlicense, btDlicense;
     Button       btSubmit;
    ImageView ivAdhaar, ivVlicense, ivDlicense;

    DocsInfo documents;
    ProgressDialog pd;


     String encodedAdhar= null, encodedDLicense= null, encodedVlicense= null;
    String MobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        MobileNumber = sharedPreferences.getString("MobileNumber",null);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(djangoBaseUrl)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        final DataInterface client = retrofit.create(DataInterface.class);

        btAdhaar = (ImageView) findViewById(R.id.bt_click_adhaar);
        btVlicense = (ImageView) findViewById(R.id.bt_click_vlicence);
        btDlicense = (ImageView) findViewById(R.id.bt_click_dlicence);
        ivAdhaar = (ImageView)findViewById(R.id.iv_adhaar);
        ivVlicense = (ImageView)findViewById(R.id.iv_vlicence);
        ivDlicense = (ImageView)findViewById(R.id.iv_dlicence);
        btSubmit = (Button) findViewById(R.id.bt_submit);

        SharedPreferences pref = getSharedPreferences("data",Context.MODE_PRIVATE);
        int i = pref.getInt("i",0);

        if(i == 1){

            btAdhaar.setEnabled(false);
            btDlicense.setEnabled(false);
            btVlicense.setEnabled(false);
            btSubmit.setEnabled(false);

            AlertDialog.Builder noteBuilder = new AlertDialog.Builder(DocumentsActivity.this);
            noteBuilder.setTitle("Documents");
            noteBuilder.setMessage("You have already submitted your details");
            noteBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(DocumentsActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }).create();
            noteBuilder.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(DocumentsActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            },3500);



        }

        if (i!=1){
            AlertDialog.Builder noteBuilder = new AlertDialog.Builder(DocumentsActivity.this);
            noteBuilder.setTitle("Document Submission");
            noteBuilder.setMessage("Documents can be submitted at once only. You cannot rewind it after clicking the " +
                    "submit button!");
            noteBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create();
            noteBuilder.show();
        }



        btAdhaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_ADHAAR);
                }
            }
        });
        btDlicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_DLICENSE);
            }
        }});

        btVlicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_VLICENSE);
                }
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd = new ProgressDialog(DocumentsActivity.this);
                pd.setMessage("Uploading documents. Please wait.");
                pd.show();
                documents = new DocsInfo(Long.parseLong(MobileNumber),encodedAdhar,encodedDLicense,encodedVlicense);
                Call<DocsInfo> docsCall = client.postShopperDocuments(documents,getApplicationContext().getSharedPreferences("Tokenkey",Context.MODE_PRIVATE).getString("token",null));
                docsCall.enqueue(new Callback<DocsInfo>() {
                    @Override
                    public void onResponse(Call<DocsInfo> call, Response<DocsInfo> response) {
                        Log.i("DONE"," " + documents.getShopperPhoneNo());
                        pd.dismiss();
                        Toast.makeText(DocumentsActivity.this, "Documents Uploaded ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DocumentsActivity.this, HomeActivity.class);
                        SharedPreferences pref = getSharedPreferences("data",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putInt("i",1);
                        editor.apply();
                        btAdhaar.setEnabled(false);
                        btDlicense.setEnabled(false);
                        btVlicense.setEnabled(false);
                        btSubmit.setEnabled(false);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<DocsInfo> call, Throwable t) {


                    }
                });

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE_ADHAAR && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivAdhaar.setImageBitmap(imageBitmap);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (imageBitmap != null) {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            }
            byte[] imageBytes = baos.toByteArray();
            encodedAdhar = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            Log.i("TAG",encodedAdhar);

        }
        else if (requestCode == REQUEST_IMAGE_CAPTURE_DLICENSE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivDlicense.setImageBitmap(imageBitmap);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (imageBitmap != null) {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            }
            byte[] imageBytes = baos.toByteArray();
            encodedDLicense = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            Log.i("TAG",encodedDLicense);

        }
        else if (requestCode == REQUEST_IMAGE_CAPTURE_VLICENSE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivVlicense.setImageBitmap(imageBitmap);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (imageBitmap != null) {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            }
            byte[] imageBytes = baos.toByteArray();
            encodedVlicense = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        }
        else{
            Toast.makeText(getApplicationContext(),"Upload Images",Toast.LENGTH_SHORT).show();
        }
    }
}
