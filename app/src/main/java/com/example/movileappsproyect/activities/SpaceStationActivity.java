package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.model.SpaceStationModel;
import com.example.movileappsproyect.util.storage.FileManage;
import com.example.movileappsproyect.util.storage.SpaceStationDB;
import com.example.movileappsproyect.util.storage.SpaceStationHelper;

import java.util.List;

public class SpaceStationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_station);
        String elem = getIntent().getStringExtra("elementoSeleccionado");

        SpaceStationHelper helper = new SpaceStationHelper(this);
        SpaceStationDB db = new SpaceStationDB(helper);
        SpaceStationModel result = db.getStation(elem);

        result.setbImage(FileManage.getImg(result.getStoreUrl(), this));
        showStation(result);

        //listeners de botones
        Button btn_share = findViewById(R.id.space_station_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, result.getUrl());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        Button btn_share_img = findViewById(R.id.space_station_share_img);
        btn_share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileManage.saveImg(result.getNombre(), SpaceStationActivity.this, result.getbImage());

                String bitmapPath = MediaStore.Images.Media.insertImage(view.getContext().getContentResolver(),
                        result.getbImage(), "Image", "Image to share");
                Uri bitmapUri = Uri.parse(bitmapPath);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                SpaceStationActivity.this.startActivity(Intent.createChooser(intent, "Compartir imagen"));
            }
        });

    }

    public void showStation(SpaceStationModel result) {
        ((TextView)findViewById(R.id.station_title)).setText(result.getNombre());
        ((TextView)findViewById(R.id.station_deorbited)).append(":   "+ result.getDeorbited());
        ((TextView)findViewById(R.id.station_description)).setText(result.getDescription());
        ((TextView)findViewById(R.id.station_orbit)).append(":   "+ result.getOrbit());
        ((TextView)findViewById(R.id.station_founded)).append(":   "+ result.getFounded());
        ((ImageView)findViewById(R.id.station_img)).setImageBitmap(result.getbImage());
    }
}