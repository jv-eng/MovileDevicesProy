package com.example.movileappsproyect.util.threads;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.example.movileappsproyect.activities.LocationActivity;
import com.example.movileappsproyect.model.localizationModels.DeviceLocationModel;
import com.example.movileappsproyect.model.localizationModels.ISSLocationModel;
import com.example.movileappsproyect.model.localizationModels.LocationModel;
import com.example.movileappsproyect.util.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LocationThread implements Runnable {
    private final String LOCALIZATION_URL = "https://api.what3words.com/v3/convert-to-3wa?key=BLNWEEO3&coordinates=";//40.4165%2C-3.70256&language=es&format=json";
    private final String ISS_LOCALIZATION = "http://api.open-notify.org/iss-now.json";
    private Context ctx;
    private double device_long;
    private double device_lat;


    public LocationThread(Context ctx, double device_long, double device_lat) {
        this.ctx = ctx;
        this.device_long = device_long;
        this.device_lat = device_lat;
    }

    @Override
    public void run() {

        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((LocationActivity)ctx).prepareUIForDownload();
            }
        });

        //url
        String imgURL = "https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/" +
                "spacestation_images/international2520space2520station_image_20190220215716.jpeg";
        Bitmap b = NetworkUtil.readImageHTTPGet(imgURL);
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((LocationActivity)ctx).setImg(b);
            }
        });


        //variables
        LocationModel res = new LocationModel();

        //task
        String jsonIssPos = NetworkUtil.getHTTPText(ISS_LOCALIZATION);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ISSLocationModel iss_pos = gson.fromJson(jsonIssPos, ISSLocationModel.class);

        String jsonDevicePos = NetworkUtil.getHTTPText(LOCALIZATION_URL
                + device_long + "%2C" + device_lat + "&language=es&format=json");
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        DeviceLocationModel devicePos = gson.fromJson(jsonDevicePos, DeviceLocationModel.class);

        //calculate distance
        double distance = calculateDistance(device_lat, device_long,Double.parseDouble(iss_pos.getLatitude()),
                Double.parseDouble(iss_pos.getLongitude()));

        //store result
        res.setDevice(devicePos);
        res.setIss(iss_pos);
        res.setDistanceToISS(distance);

        //show result
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((LocationActivity)ctx).showDownloadResults(res);
                ((LocationActivity)ctx).prepareUIAfterDownload();
            }
        });
    }

    //calcualr distancia en kilometros
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radio de la Tierra en kilómetros

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Distancia en kilómetros
    }
}
