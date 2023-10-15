package com.example.movileappsproyect.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class NetworkUtil {

    //download http text
    public static String getHTTPText(String url) {
        StringBuilder response = new StringBuilder();
        try {
            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();
        } catch (Exception e){
            Log.e("NetworkUtil",e.toString());
        }

        return response.toString();
    }

    //download an image
    public static Bitmap readImageHTTPGet(String url) {
        Bitmap response = null;
        try {
            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            response = BitmapFactory.decodeStream(connection.getInputStream());

        } catch (Exception e){
            Log.e("NetworkUtil",e.toString());
        }

        return response;
    }

    //check network connection
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
        }

        return false;
    }
}
