package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.model.DayPictureModel;
import com.example.movileappsproyect.util.storage.FileManage;
import com.example.movileappsproyect.util.threads.DayPictureDownloadThread;
import com.example.movileappsproyect.util.threads.LoginThread;

import java.io.File;

public class PictureDayActivity extends AppCompatActivity {

    private ProgressDialog progressDialog; //otra opcion es  ProgressBar
    private DayPictureModel results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_day);

        //obtener datos
        Thread th = new Thread(new DayPictureDownloadThread(this));
        th.start();

        //lógica de botones
        Button btn_share_img = findViewById(R.id.pict_day_share_img);
        btn_share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileManage.saveImg(results.getTitle(), PictureDayActivity.this, results.getbImage());

                String bitmapPath = MediaStore.Images.Media.insertImage(view.getContext().getContentResolver(),
                        results.getbImage(), "Image", "Image to share");
                Uri bitmapUri = Uri.parse(bitmapPath);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                PictureDayActivity.this.startActivity(Intent.createChooser(intent, "Compartir imagen"));
            }
        });

        Button b = findViewById(R.id.pict_day_share);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, results.getUrl());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
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
        this.results = results;
        ImageView tv = findViewById(R.id.picture_day_img);
        tv.setImageBitmap(results.getbImage());
        ((TextView)findViewById(R.id.pict_day_title)).setText(results.getTitle());
        ((TextView)findViewById(R.id.pict_day_date)).setText(results.getDate());
        ((TextView)findViewById(R.id.pict_day_explanation)).setText(results.getExplanation());
    }
}