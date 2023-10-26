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
import com.example.movileappsproyect.util.storage.FileManage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.LinkedList;
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

        List<SpaceStationModel> stations_res = new LinkedList<>();
        SpaceStationRequestModel stations_req;
        String url = baseUrl;

        do {
            //task
            String jsonStations = NetworkUtil.getHTTPText(url);
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            stations_req = gson.fromJson(jsonStations, SpaceStationRequestModel.class);
            List<SpaceStationModel> stations = Arrays.asList(stations_req.getResults());
            //edit objects
            for (SpaceStationModel station: stations) {
                //modify name
                station.setNombre(station.getNombre().replaceAll(" ",""));
                //get image
                Bitmap b = NetworkUtil.readImageHTTPGet(station.getImage());
                station.setbImage(b);
                //guardar imagen
                FileManage.saveImg(station.getNombre(), ctx, b);
                //check deorbited
                if (station.getDeorbited() == null) station.setDeorbited("Still in orbit");
                //store object
                stations_res.add(station);
            }
            if (stations_req.getNext() != null) url = stations_req.getNext();
        } while (stations_req.getNext() != null);


        //show result
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((SpaceStationListActivity)ctx).showDownloadResults(stations_res);
                ((SpaceStationListActivity)ctx).prepareUIAfterDownload();
            }
        });
    }
}
