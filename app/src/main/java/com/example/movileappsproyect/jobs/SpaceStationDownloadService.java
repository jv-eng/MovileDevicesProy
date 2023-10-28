package com.example.movileappsproyect.jobs;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.example.movileappsproyect.util.threads.SpaceStationListDownloadThread;

public class SpaceStationDownloadService extends JobService {
    private static final String TAG = SpaceStationDownloadService.class.getName();
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(TAG,"Descargando estaciones");
        Thread th = new Thread(new SpaceStationListDownloadThread(SpaceStationDownloadService.this));
        th.start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
