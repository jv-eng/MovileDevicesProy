package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.model.SpaceStationModel;
import com.example.movileappsproyect.util.SpaceStationListAdapter;
import com.example.movileappsproyect.util.threads.SpaceStationListDownloadThread;

import java.util.List;

public class SpaceStationListActivity extends AppCompatActivity {

    private ProgressDialog progressDialog; //otra opcion es  ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_station_list);

        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pillar directamente de la base de datos, usamos un job para descargar
                if (MainActivity.internetFlag) { //hay -> descargamos
                    Thread th = new Thread(new SpaceStationListDownloadThread(SpaceStationListActivity.this));
                    th.start();
                } else { }
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

    public void showDownloadResults(List<SpaceStationModel> results) {
        ListView lv = findViewById(R.id.list);
        SpaceStationListAdapter adapter = new SpaceStationListAdapter(this, results);
        lv.setAdapter(adapter);
    }
}