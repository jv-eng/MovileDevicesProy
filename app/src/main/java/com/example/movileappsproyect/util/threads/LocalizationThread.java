package com.example.movileappsproyect.util.threads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.movileappsproyect.activities.LocalizationActivity;
import com.example.movileappsproyect.model.localizationModels.DeviceLocationModel;
import com.example.movileappsproyect.model.localizationModels.ISSLocationModel;
import com.example.movileappsproyect.model.localizationModels.LocationModel;
import com.example.movileappsproyect.util.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LocalizationThread implements Runnable {
    private final String LOCALIZATION_URL = "https://api.what3words.com/v3/convert-to-3wa?key=BLNWEEO3&coordinates=";//40.4165%2C-3.70256&language=es&format=json";
    private final String ISS_LOCALIZATION = "http://api.open-notify.org/iss-now.json";
    private Context ctx;

    public LocalizationThread(Context ctx) {this.ctx = ctx;}

    @Override
    public void run() {
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((LocalizationActivity)ctx).prepareUIForDownload();
            }
        });

        //variables
        LocationModel res = new LocationModel();
        double longitude = 40.4165;
        double lat = -3.70256;

        //task
        String jsonIssPos = NetworkUtil.getHTTPText(ISS_LOCALIZATION);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ISSLocationModel iss_pos = gson.fromJson(jsonIssPos, ISSLocationModel.class);

        String jsonDevicePos = NetworkUtil.getHTTPText(LOCALIZATION_URL
                + longitude + "%2C" + lat + "&language=es&format=json");
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        DeviceLocationModel devicePos = gson.fromJson(jsonDevicePos, DeviceLocationModel.class);

        //store result
        res.setDevice(devicePos);
        res.setIss(iss_pos);

        //show result
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((LocalizationActivity)ctx).showDownloadResults(res);
                ((LocalizationActivity)ctx).prepareUIAfterDownload();
            }
        });
    }
}
