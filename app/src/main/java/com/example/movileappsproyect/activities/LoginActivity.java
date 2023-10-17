package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.util.threads.DayPictureDownloadThread;
import com.example.movileappsproyect.util.threads.LocalizationThread;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Thread th = new Thread(new LocalizationThread(LoginActivity.this));
        th.start();

    }
}