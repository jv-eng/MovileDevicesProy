package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.model.SpaceStationModel;
import com.example.movileappsproyect.util.SpaceStationListAdapter;
import com.example.movileappsproyect.util.storage.FileManage;
import com.example.movileappsproyect.util.storage.SpaceStationDB;
import com.example.movileappsproyect.util.storage.SpaceStationHelper;

import java.io.File;
import java.util.List;

public class SpaceStationListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_station_list);

        SpaceStationHelper helper = new SpaceStationHelper(this);
        SpaceStationDB db = new SpaceStationDB(helper);
        List<SpaceStationModel> results = db.getAll();

        for (SpaceStationModel station: results) {
            station.setbImage(FileManage.getImg(station.getStoreUrl(),this));
            Log.i("estacion  " + station.getNombre(), station.getNombre()+".jpeg");
        }

        Log.e("aquiiiii",String.valueOf(new File(results.get(0).getNombre()+".jpeg").exists()));

        ListView lv = findViewById(R.id.space_station_list);
        SpaceStationListAdapter adapter = new SpaceStationListAdapter(this, results);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String elem = results.get(i).getNombre();
                Intent intent = new Intent(SpaceStationListActivity.this, SpaceStationActivity.class);
                Log.i("elemento", elem);
                intent.putExtra("elementoSeleccionado", elem);
                startActivity(intent);
            }
        });
    }

}