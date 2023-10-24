package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.model.DayPictureModel;
import com.example.movileappsproyect.util.threads.DayPictureDownloadThread;

public class PictureDayActivity extends AppCompatActivity {

    private ProgressDialog progressDialog; //otra opcion es  ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_day);
        Button b = findViewById(R.id.buttonPict);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread th = new Thread(new DayPictureDownloadThread(PictureDayActivity.this));
                th.start();
            }
        });
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

    public void showDownloadResults(DayPictureModel results) {
        ImageView tv = findViewById(R.id.picture_day_img);
        tv.setImageBitmap(results.getbImage());
        ((TextView)findViewById(R.id.pict_day_title)).setText(results.getTitle());
        ((TextView)findViewById(R.id.pict_day_date)).setText(results.getDate());
        ((TextView)findViewById(R.id.pict_day_explanation)).setText(results.getExplanation());
    }
}