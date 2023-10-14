package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.movileappsproyect.R;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i  = new Intent(this, SpaceStationListActivity.class);
        startActivity(i);
    }
}