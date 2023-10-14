package com.example.movileappsproyect.util.threads;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.example.movileappsproyect.model.DayPictureModel;
import com.example.movileappsproyect.util.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

public class DayPictureDownloadThread implements Runnable {

    private Context ctx;
    private final String TOKEN = "XDTlhh2z5xaGSDpc4ZTDvbmGGIuezEwYgkARpRyz ";
    private final String URL = "https://api.nasa.gov/planetary/apod?api_key=" + TOKEN;
    @Override
    public void run() {
        //prepare interface
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //((SpaceStationListActivity)ctx).prepareUIForDownload();
            }
        });

        //task
        String jsonDayPicture = NetworkUtil.getHTTPText(URL);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        DayPictureModel picture = gson.fromJson(jsonDayPicture, DayPictureModel.class);
        //get image
        Bitmap b = NetworkUtil.readImageHTTPGet(picture.getUrl());
        picture.setbImage(b);


        //show result
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //((SpaceStationListActivity)ctx).showDownloadResults(stations);
                //((SpaceStationListActivity)ctx).prepareUIAfterDownload();
            }
        });
    }
}
