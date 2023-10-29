package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

                    Log.e("coordenadas", "conseguimos coordenadas");
                    Thread th = new Thread(new LocationThread(LocationActivity.this, device_lat, device_long));
                    th.start();
                } else {
                    Log.e(" no coordenadas", "error");
                    LocationActivity.setValues(40.4165,-3.70256);
                }
            }
        });
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
        ((TextView)findViewById(R.id.location_distance_res)).setText(results.getDistanceToISS() + "  km");
    }

    public void setImg(Bitmap img) {
        ImageView tv = findViewById(R.id.location_img);
        tv.setImageBitmap(img);
    }

}