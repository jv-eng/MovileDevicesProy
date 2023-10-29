package com.example.movileappsproyect.jobs;

import android.Manifest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.movileappsproyect.R;

public class NotificationService extends JobService {

    private static final String TAG = NotificationService.class.getName();
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        if (jobCancelled) return true;

        Context baseContext = getBaseContext();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(baseContext, "notification")
                .setSmallIcon(R.mipmap.ic_logo)
                .setContentTitle("New Image!")
                .setContentText("There is a new image of the day!")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompact = NotificationManagerCompat.from(baseContext);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            notificationManagerCompact.notify(123, builder.build());
            Log.d(TAG, "Notification was sent");
            jobFinished(jobParameters, true);
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        jobCancelled = true;
        return false;
    }

}