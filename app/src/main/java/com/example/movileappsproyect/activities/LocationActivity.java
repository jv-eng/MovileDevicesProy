package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.model.localizationModels.LocationModel;
import com.example.movileappsproyect.util.threads.LocationThread;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class LocationActivity extends AppCompatActivity {

    private ProgressDialog progressDialog; //otra opcion es  ProgressBar

    public static double device_long;
    public static double device_lat;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        askForPermissionsGrant();

        if (!MainActivity.isGeoPermision()) {
            Toast.makeText(this, "No GPS connection, using default values",
                    Toast.LENGTH_SHORT).show();
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation().addOnSuccessListener((Activity) this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null && location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                    LocationActivity.setValues(location.getLatitude(), location.getLongitude());

                    Log.e("corrdenadas", "conseguimos coordenadas");
                    Log.e("lat", String.valueOf(LocationActivity.device_lat));
                    Log.e("long", String.valueOf(LocationActivity.device_long));
                    Thread th = new Thread(new LocationThread(LocationActivity.this, device_lat, device_long));
                    th.start();
                } else {
                    Log.e(" no coordenadas", "error");
                    LocationActivity.setValues(40.4165,-3.70256);
                }
            }
        });

        //Log.e("lat", String.valueOf(device_lat));

        Log.e("lat", String.valueOf(LocationActivity.device_lat));
        Log.e("long", String.valueOf(LocationActivity.device_long));

        //descarga de información
        //Thread th = new Thread(new LocationThread(this, device_long, device_lat));
        //th.start();
    }

    public static void setValues(double lat, double longitude) {
        LocationActivity.device_long = longitude;
        LocationActivity.device_lat = lat;
    }

    public void prepareUIForDownload() {
        progressDialog  = new ProgressDialog(this);
        progressDialog.setMessage("Descargando....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void prepareUIAfterDownload() {
        progressDialog.dismiss();
    }

    public void showDownloadResults(LocationModel results) {
        ((TextView)findViewById(R.id.location_device_country)).append("   "+ results.getDevice().getCountry());
        ((TextView)findViewById(R.id.location_device_nearest_place)).append("   "+ results.getDevice().getPlace());
        ((TextView)findViewById(R.id.location_device_url)).append("   "+ results.getDevice().getMap_url());
        ((TextView)findViewById(R.id.location_iss_lat)).append("   "+ results.getIss().getLatitude());
        ((TextView)findViewById(R.id.location_iss_long)).append("   "+ results.getIss().getLongitude());
        ((TextView)findViewById(R.id.location_iss_time)).append("   "+ results.getIss().getTimestamp());
        ((TextView)findViewById(R.id.location_distance_res)).setText(String.valueOf(results.getDistanceToISS()) + "  km");
    }

    public void setImg(Bitmap img) {
        ImageView tv = findViewById(R.id.location_img);
        tv.setImageBitmap(img);
    }

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public void askForPermissionsGrant() {
        // Here, thisActivity is the current activity

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("We need to access your location")
                        .setMessage("We want to track every breath you take")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(LocationActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                            }
                        });
                builder.create().show();
                Log.i(this.getLocalClassName(), "permisos obtenidos");

            }
            else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        } else {
            // Permission has already been granted

            Toast.makeText(this, "Permisos obtenidos", Toast.LENGTH_SHORT).show();
            Log.i(this.getLocalClassName(), "permisos ya obtenidos");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "callback de permisos, concedido", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "callback de permisos, no concedidos", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }


}