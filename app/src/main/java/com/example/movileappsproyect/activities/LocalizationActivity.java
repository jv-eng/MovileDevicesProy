package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.model.DayPictureModel;
import com.example.movileappsproyect.model.localizationModels.LocationModel;

public class LocalizationActivity extends AppCompatActivity {

    private ProgressDialog progressDialog; //otra opcion es  ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localization);
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
        //ImageView tv = findViewById(R.id.imageView);
        //tv.setImageBitmap(results.getbImage());
    }

    //calcualr distancia en kilometros
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radio de la Tierra en kilómetros

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Distancia en kilómetros
    }
}