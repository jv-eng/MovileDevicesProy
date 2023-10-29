package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.jobs.NotificationService;
import com.example.movileappsproyect.jobs.SpaceStationDownloadService;
import com.example.movileappsproyect.util.NetworkUtil;
import com.example.movileappsproyect.util.storage.PreferencesManage;
import com.example.movileappsproyect.util.storage.SpaceStationHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static int NOTIFICATION_ID = 608, SPACE_STATION_DOWNLOAD_ID = 609;

    private final static String TAG = MainActivity.class.getName();
    private static boolean geoPermision = false;
    //add flag primera insercion
    public static boolean firstJob = true;

    public static boolean isGeoPermision() {
        return geoPermision;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDB();

        //comprobar job de descarga de estaciones
        if(!isJobServiceRunning(SPACE_STATION_DOWNLOAD_ID)) startBatchUpdateJob();
        else Log.e("ERRRRORR","no se descargan");

        //comprobar notificaciones
        if(!isJobServiceRunning(NOTIFICATION_ID)) {
            createNotificationChannel();
            startNotificationJob();
        } else Log.e("ERRRRORR","no hay notificaciones");

        //comprobar permisos de geolocalizacion
        askForPermissionsGrant();

        //comprobar si hay conexion a internet
        if (!NetworkUtil.isNetworkAvailable(this)) {
            Log.e("Internet Connection","No hay internet");
            Toast.makeText(this, "Por favor, revise su conexión",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void checkDB() {
        String query = "SELECT * FROM station;";
        SpaceStationHelper helper = new SpaceStationHelper(this);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(query, new String[]{});
        if (c.getCount() > 0) firstJob = false;
        c.close();
    }

    private void startNotificationJob(){
        ComponentName comName = new ComponentName(this, NotificationService.class);
        JobInfo info = new JobInfo.Builder(NOTIFICATION_ID, comName)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.schedule(info);
    }

    private void startBatchUpdateJob(){
        ComponentName comName = new ComponentName(this, SpaceStationDownloadService.class);
        JobInfo info = new JobInfo.Builder(SPACE_STATION_DOWNLOAD_ID, comName)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.schedule(info);
    }

    private void createNotificationChannel(){
        CharSequence name = "notificationChannel";
        String description = "Main notification channel";

        NotificationChannel channel = new NotificationChannel("notification", name, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //comprobar token
        if (!PreferencesManage.userExists(this)) {
            //comprobar
            Intent i  = new Intent(this, LoginActivity.class);
            startActivity(i);
        } else {
            //arrancar el menu de opciones
            Intent i  = new Intent(this, MenuActivity.class);
            startActivity(i);
        }
    }

    //comprobar jobs activos en el sistema
    private boolean isJobServiceRunning(int id) {
        JobScheduler scheduler = MainActivity.this.getSystemService(JobScheduler.class);
        List<JobInfo> actJobs = scheduler.getAllPendingJobs();
        for (JobInfo job: actJobs) {
            if(job.getId() == id)
                return true;
        }
        return false;
    }

    //comprobar permisos de geolocalizacion
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public void askForPermissionsGrant() {
        // Here, thisActivity is the current activity

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("We need to access your location")
                        .setMessage("We want to track every breath you take")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                            }
                        });
                builder.create().show();
                Log.i(this.getLocalClassName(), "permisos obtenidos");
                this.geoPermision = true;
            }
            else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        } else {
            // Permission has already been granted
            this.geoPermision = true;
            //Toast.makeText(this, "Permisos obtenidos", Toast.LENGTH_SHORT).show();
            //Log.i(this.getLocalClassName(), "permisos ya obtenidos");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "callback de permisos, concedido", Toast.LENGTH_SHORT).show();
                    Log.i("callback","callback de permisos, concedido");
                    this.geoPermision = true;
                } else {
                    //Toast.makeText(this, "callback de permisos, no concedidos", Toast.LENGTH_SHORT).show();
                    Log.i("callback","callback de permisos, no concedidos");
                }
                return;
        }
    }
}