package com.example.movileappsproyect.jobs;

import android.Manifest;
import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.activities.MainActivity;

public class NotificationService extends JobService {

    private static final String TAG = NotificationService.class.getName();
    private boolean jobCancelled = false;
    private int cont = 1;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        if (jobCancelled) return true;

        Notification.Builder nBuilder = MainActivity.getHandler().createNotificationChannels(
                getString(R.string.notification_title), getString(R.string.notification_msg));
        MainActivity.getHandler().getManager().notify(cont++,nBuilder.build());
        MainActivity.getHandler().publishGroup();

        Log.i("Notificaciones","Notificación lanzada");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        jobCancelled = true;
        return false;
    }

}