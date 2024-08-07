package com.example.movileappsproyect.util.threads;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.example.movileappsproyect.activities.PictureDayActivity;
import com.example.movileappsproyect.model.DayPictureModel;
import com.example.movileappsproyect.util.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DayPictureDownloadThread implements Runnable {

    private Context ctx;
    private final String TOKEN = "XDTlhh2z5xaGSDpc4ZTDvbmGGIuezEwYgkARpRyz ";
    private final String URL = "https://api.nasa.gov/planetary/apod?api_key=" + TOKEN;

    public DayPictureDownloadThread(Context contexto) {
        this.ctx = contexto;
    }

    @Override
    public void run() {
        //prepare interface
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((PictureDayActivity)ctx).prepareUIForDownload();
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
                ((PictureDayActivity)ctx).showDownloadResults(picture);
                ((PictureDayActivity)ctx).prepareUIAfterDownload();
            }
        });
    }
}
