package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.model.SpaceStationModel;
import com.example.movileappsproyect.util.SpaceStationListAdapter;
import com.example.movileappsproyect.util.storage.FileManage;
import com.example.movileappsproyect.util.storage.SpaceStationDB;
import com.example.movileappsproyect.util.storage.SpaceStationHelper;
import com.example.movileappsproyect.util.threads.SpaceStationListDownloadThread;

import java.io.File;
import java.util.List;

public class SpaceStationListActivity extends AppCompatActivity {

    private ProgressDialog progressDialog; //otra opcion es  ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_station_list);

        SpaceStationHelper helper = new SpaceStationHelper(this);
        SpaceStationDB db = new SpaceStationDB(helper);
        List<SpaceStationModel> results = db.getAll();

        for (SpaceStationModel station: results) {
            station.setbImage(FileManage.getImg(station.getNombre() + ".jpeg",this));
            Log.i("estacion  " + station.getNombre(), station.getNombre()+".jpeg");
        }

        Log.e("aquiiiii",String.valueOf(new File(results.get(0).getNombre()+".jpeg").exists()));

        ListView lv = findViewById(R.id.list);
        SpaceStationListAdapter adapter = new SpaceStationListAdapter(this, results);
        lv.setAdapter(adapter);
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

    }
}