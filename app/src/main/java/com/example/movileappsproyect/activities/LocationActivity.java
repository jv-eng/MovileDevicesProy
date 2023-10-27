package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.model.localizationModels.LocationModel;
import com.example.movileappsproyect.util.storage.FileManage;
import com.example.movileappsproyect.util.threads.LocationThread;

import java.io.File;

public class LocationActivity extends AppCompatActivity {

    private ProgressDialog progressDialog; //otra opcion es  ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //descarga de información
        Thread th = new Thread(new LocationThread(this));
        th.start();
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
        ((TextView)findViewById(R.id.location_distance_res)).setText(String.valueOf(results.getDistanceToISS()));
    }

    public void setImg(Bitmap img) {
        ImageView tv = findViewById(R.id.location_img);
        tv.setImageBitmap(img);
    }


}