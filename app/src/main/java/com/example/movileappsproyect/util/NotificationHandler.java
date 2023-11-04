package com.example.movileappsproyect.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.activities.PictureDayActivity;

public class NotificationHandler extends ContextWrapper {
    private NotificationManager manager;
    public static final String CHANNEL_ID = "1";
    private final String CHANNEL_NAME = "SPACE STATION CHANNEL";
    private final String GROUP_NAME = "SPACE_STATION_GROUP";
    private static final int GROUP_ID = 111;
    public NotificationHandler(Context base) {
        super(base);
        createChannels();
    }

    //aseguramos singleton
    public NotificationManager getManager() {
        if (manager==null)
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        return manager;
    }

    public void createChannels() {
        NotificationChannel highChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        //podemos añadir mas configuraciones
        highChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        getManager().createNotificationChannel(highChannel);
    }

    public Notification.Builder createNotificationChannels (String title, String msg) {
        //creamos el intent
        Intent intent = new Intent(this, PictureDayActivity.class);
        intent.putExtra("titulo", title);
        intent.putExtra("msg",msg);
        //configure flags
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //creariamos el pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,
                intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        //configurar la notificaicón
        return new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setGroup(GROUP_NAME)
                .setContentIntent(pendingIntent);
    }

    public void publishGroup() {
        Notification groupNotificacion = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setGroup(GROUP_NAME)
                .setGroupSummary(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
        getManager().notify(GROUP_ID,groupNotificacion);
    }
}
