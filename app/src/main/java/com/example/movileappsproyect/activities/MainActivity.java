package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.util.NetworkUtil;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getName();
    static boolean internetFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //comprobar si funcionan las notificaciones
        //si no, arrancarlas

        //comprobar si hay conexion a internet
        if (NetworkUtil.isNetworkAvailable(this)) {
            internetFlag = true;
            Log.i("Internet Connection","Tenemos internet");
        } else { Log.e("Internet Connection","No hay internet"); }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //comprobar token
        if (false) {
            //comprobar
        } else {
            //arrancar el menu de opciones
            Intent i  = new Intent(this, MenuActivity.class);
            startActivity(i);
        }
    }
}