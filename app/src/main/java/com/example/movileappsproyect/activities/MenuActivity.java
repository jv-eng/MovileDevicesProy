package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.util.NetworkUtil;
import com.example.movileappsproyect.util.storage.PreferencesManage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MenuActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int MY_PERMISSIONS_REQUEST_Notification = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        askForPermissionsGrant();
        askForPermissionsGrantNotification();

        setBtnLogic();
    }

    private void setBtnLogic() {
        Button btnSpaceStationList = findViewById(R.id.menu_list_space_station);
        btnSpaceStationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(MenuActivity.this, SpaceStationListActivity.class);
                startActivity(i);
            }
        });


        Button btnDayPicture = findViewById(R.id.menu_day_pict);
        btnDayPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isNetworkAvailable(MenuActivity.this)) {
                    Intent i  = new Intent(MenuActivity.this, PictureDayActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MenuActivity.this,
                            R.string.dayPictNoInternet, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnISSDistance = findViewById(R.id.menu_distance);
        btnISSDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(MenuActivity.this, LocationActivity.class);
                startActivity(i);
            }
        });

        FloatingActionButton btn_logut = findViewById(R.id.menu_activity_logut);
        btn_logut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesManage.removeUser(MenuActivity.this);
                Intent i  = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    public void askForPermissionsGrantNotification() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Activate notifications")
                    .setMessage("Turn on notifications if you want to know when the next image is available.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MenuActivity.this,
                                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                                    MY_PERMISSIONS_REQUEST_Notification);
                        }
                    });
            builder.create().show();
            Log.i(this.getLocalClassName(), "permisos obtenidos");
        }
    }
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
                                ActivityCompat.requestPermissions(MenuActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                            }
                        });
                builder.create().show();
                Log.i(this.getLocalClassName(), "permisos obtenidos");

            }
            else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        } else {
            // Permission has already been granted

            //Toast.makeText(this, "Permisos obtenidos", Toast.LENGTH_SHORT).show();
            Log.i(this.getLocalClassName(), "permisos ya obtenidos");
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
                    Log.i("callback","callback de permisos, concedidos");
                } else {
                    //Toast.makeText(this, "callback de permisos, no concedidos", Toast.LENGTH_SHORT).show();
                    Log.i("callback","callback de permisos, no concedidos");
                }
                return;
        }
    }
}