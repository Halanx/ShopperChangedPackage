package com.shopperapphalanx.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shopperapphalanx.Activities.OnWayStoreActivity;
import com.shopperapphalanx.POJO.ShopperInfo;
import com.shopperapphalanx.R;
import com.shopperapphalanx.app.Config;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.shopperapphalanx.GlobalClass.djangoBaseUrl;


public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker, marker, marker1, dragMarker;
    Button onOff;
    LatLng l;
    OnMapReadyCallback callback;
    ProgressBar progressBar;
    Context c;
    FloatingActionButton setLocation;

    String token;
    String regId;
    String data;

    ImageView ivShowBatches;
    String mobile, url;
    ProgressDialog progressDialog;
    ShopperInfo info;

    int i=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ivShowBatches = (ImageView) v.findViewById(R.id.iv_show_batches);
        onOff = (Button) v.findViewById(R.id.on_off);
        token = getActivity().getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token",null);
        Log.d("token_key",getActivity().getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token",null));



        if (getActivity().getSharedPreferences("Batch", Context.MODE_PRIVATE).getBoolean("isBusy", false)) {
            ivShowBatches.setVisibility(View.VISIBLE);
            onOff.setText("Busy");
            onOff.setClickable(false);

        } else {
            mobile = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE).getString("MobileNumber", null);
            url = "https://api.halanx.com/shoppers/detail/";
            Log.i("TAG", url);

            setLocation = (FloatingActionButton) v.findViewById(R.id.set_location1);
            mapFrag = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
            callback = this;
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);


            Volley.newRequestQueue(getActivity()).add(new JsonObjectRequest(Request.Method.GET, djangoBaseUrl + "shoppers/batches/current/", new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        if(response.getJSONArray("results").length()>0){

                            getActivity().getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE).edit().putString("batch_id",response.getJSONArray("results").getJSONObject(0).getString("id")).commit();

                            ivShowBatches.setVisibility(View.VISIBLE);
                            onOff.setText("Busy");
                            onOff.setClickable(false);

                        } else
                            {

                                ivShowBatches.setVisibility(View.GONE);


                                progressDialog = new ProgressDialog(getActivity());
                                progressDialog.setMessage("Please wait a moment");
                                progressDialog.setCancelable(false);
                                progressDialog.show();


                                Volley.newRequestQueue(getActivity()).add(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                        info = new GsonBuilder().create().fromJson(response, ShopperInfo.class);
                                        progressDialog.dismiss();

                                        if (info.getOnline()) {
                                            checkLocationServices();
                                            mapFrag.getMapAsync(callback);
                                            progressBar.setVisibility(View.VISIBLE);
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(c, "Network error", Toast.LENGTH_SHORT).show();

                                    }
                                }){
                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Content-Type", "application/json");
                                        params.put("Authorization", getActivity().getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token",null));
                                        return params;
                                    }

                                });



                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }}, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("Authorization", getActivity().getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token",null));
                    return params;
                }

            });


            onOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String status = onOff.getText().toString();

                    switch (status) {
                        case "GO ONLINE":
                            //User goes online

                            progressBar.setVisibility(View.VISIBLE);
                            onOff.setVisibility(View.INVISIBLE);
                            checkLocationServices();

                            JSONObject obj = new JSONObject();
                            try {
                                Log.d("done", "done");
                                obj.put("IsOnline", true);
                                obj.put("Busy", false);
                                obj.put("AvailableDate", new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                                obj.put("AvailableFrom", new java.text.SimpleDateFormat("HH:mm:ss").format(new Date()));
                                obj.put("AvailableTo", (new SimpleDateFormat("HH:mm:ss").format(new Date().getTime()+(01 * 60 * 60 * 1000))));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Volley.newRequestQueue(getActivity()).add(new JsonObjectRequest(Request.Method.PATCH, url, obj, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.i("TAG", "ONLINE");

                                    progressBar.setVisibility(View.INVISIBLE);
                                    onOff.setVisibility(View.VISIBLE);
                                    mapFrag.getMapAsync(callback);


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
                                    params.put("Authorization", getActivity().getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token",null));
                                    return params;
                                }

                            }).setRetryPolicy(new RetryPolicy() {
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

                                }
                            });

                            break;


                        case "GO OFFLINE":
                            //User goes offline

                            onOff.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);

                            JSONObject obj1 = new JSONObject();
                            try {
                                obj1.put("IsOnline", false);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Volley.newRequestQueue(getActivity()).add(new JsonObjectRequest(Request.Method.PATCH, url, obj1, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("online status", " offline ");
                                    //    getActivity().stopService(new Intent(getActivity(),LocationSharingService.class));
                                    stopLocation();
                                    progressBar.setVisibility(View.INVISIBLE);
                                    onOff.setVisibility(View.VISIBLE);
                                    onOff.setBackgroundResource(R.color.colorPrimaryDark);
                                    onOff.setText("GO ONLINE");

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
                                    params.put("Authorization", getActivity().getSharedPreferences("Tokenkey", Context.MODE_PRIVATE).getString("token",null));
                                    return params;
                                }

                            }).setRetryPolicy(new RetryPolicy() {
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

                                }
                            });


                            break;
                    }
                }
            });


            setLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCurrLocationMarker != null) {
                        checkLocationServices();
                        checkLocationPermission();
                        mapFrag.getMapAsync(callback);
                        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                                mMessageReceiver, new IntentFilter("GPSLocationUpdates"));

                        Toast.makeText(getContext(), "Your current location!", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }



        ivShowBatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OnWayStoreActivity.class);
                Log.d("done","done");
                startActivity(intent);
            }
        });
        return v;

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            double lat = intent.getDoubleExtra("latitude", 0.0);
            double lon = intent.getDoubleExtra("longitude", 0.0);

            l = new LatLng(lat, lon);

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(l);
            markerOptions.title("Your Location");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(l).zoom(10).build();

            mCurrLocationMarker.setDraggable(true);
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        dragMarker = mGoogleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker
                (BitmapDescriptorFactory.HUE_VIOLET)).title("Drag Location").position(new LatLng(0, 0)));
        dragMarker.setVisible(false);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);

                Log.i("TAG", "BUILD");
            } else {
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(300000); //5 minutes
        mLocationRequest.setFastestInterval(200000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {


        mLastLocation = location;

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.setDraggable(true);
            mCurrLocationMarker.remove();
        }


        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Your Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14).build();

        mCurrLocationMarker.setDraggable(true);
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        progressBar.setVisibility(View.INVISIBLE);
        onOff.setBackgroundResource(R.color.colorGrey);
        onOff.setText("GO OFFLINE");
        onOff.setVisibility(View.VISIBLE);

        JSONObject objShopperLatLong = new JSONObject();


        if (isAdded()) {


            try {
                objShopperLatLong.put("Latitude", location.getLatitude());
                objShopperLatLong.put("Longitude", location.getLongitude());
                if (i == 0) {


                    SharedPreferences prefa = getActivity().getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);

                    regId = prefa.getString("regId", "0");
                    Log.d("registration", regId);
                    objShopperLatLong.put("GcmId", regId);
                }

                i++;


            } catch (JSONException e) {
                e.printStackTrace();
            }
            Volley.newRequestQueue(getActivity()).add(new JsonObjectRequest(Request.Method.PATCH, url, objShopperLatLong, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

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
                    params.put("Authorization",token);
                    return params;
                }

            });
        }


        //User later
//        GoogleDirection.withServerKey("AIzaSyAUvuRnFQtjzG29GBADSUybqTOpn-WXKQI").
//                from(new LatLng(location.getLatitude(), location.getLongitude())).to(shopperLocation)
//                .avoid(AvoidType.FERRIES).avoid(AvoidType.HIGHWAYS).execute(new DirectionCallback() {
//            @Override
//            public void onDirectionSuccess(Direction direction, String rawBody) {
//                String status = direction.getStatus();
//                if (status.equals(RequestResult.OK)) {
//                    Log.i("TAG", "Polylines");
//
//                    Route route = direction.getRouteList().get(0);
//                    Leg leg = route.getLegList().get(0);
//                    ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
//                    PolylineOptions polylineOptions = DirectionConverter.
//                            createPolyline(c.getApplicationContext(), directionPositionList, 5, Color.RED);
//                    mGoogleMap.addPolyline(polylineOptions);
//
//                } else if (status.equals(RequestResult.NOT_FOUND)) {
//                }
//            }
//
//            @Override
//            public void onDirectionFailure(Throwable t) {
//            }
//        });

    }


    void stopLocation() {

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(28.7125, 77.1359)).zoom(6).build();

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
            mCurrLocationMarker.setVisible(false);
        }
        if (marker != null) {
            marker.remove();
            marker.setVisible(false);
        }
        if (marker1 != null) {
            marker1.remove();
            marker1.setVisible(false);
        }

        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mGoogleMap.clear();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(false);
    }

    private void checkLocationServices() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Location services not enabled");  // GPS not found
            builder.setMessage("Kindly enable the location services to proceed"); // Want to enable?
            builder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    progressBar.setVisibility(View.INVISIBLE);
                    onOff.setVisibility(View.VISIBLE);
                    onOff.setBackgroundResource(R.color.colorPrimaryDark);
                    onOff.setText("GO ONLINE");
                }
            });
            builder.create().show();
            return;
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(getActivity().getApplicationContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }


}





