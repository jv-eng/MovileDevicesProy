package com.example.movileappsproyect.util.threads;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.movileappsproyect.activities.MainActivity;
import com.example.movileappsproyect.activities.SpaceStationListActivity;
import com.example.movileappsproyect.model.SpaceStationModel;
import com.example.movileappsproyect.model.SpaceStationRequestModel;
import com.example.movileappsproyect.util.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.List;

public class SpaceStationListDownloadThread implements Runnable {

    private Context ctx;
    private final String baseUrl = "https://ll.thespacedevs.com/2.2.0/spacestation/";
    public SpaceStationListDownloadThread(Context contexto) {
        this.ctx = contexto;
    }

    @Override
    public void run() {
        //prepare interface
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((SpaceStationListActivity)ctx).prepareUIForDownload();
            }
        });


        //task
        String jsonStations = NetworkUtil.getHTTPText(baseUrl);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        SpaceStationRequestModel stations_req = gson.fromJson(jsonStations, SpaceStationRequestModel.class);
        List<SpaceStationModel> stations = Arrays.asList(stations_req.getResults());
        //edit objects
        for (SpaceStationModel station: stations) {
            //get image
            Bitmap b = NetworkUtil.readImageHTTPGet(station.getImage());
            station.setbImage(b);
            //check deorbited
            if (station.getDeorbited() == null) station.setDeorbited("Still active");
        }


        //show result
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((SpaceStationListActivity)ctx).showDownloadResults(stations);
                ((SpaceStationListActivity)ctx).prepareUIAfterDownload();
            }
        });
    }
}
