package com.improve10x.doordare;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification {

    public static void pushNotification(Context context, String notificationTitle, String notificationMsg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("Do/Dare", "Do/Dare", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(context, NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder
                (context, "Do/Dare");
        notificationBuilder.setContentTitle(notificationTitle);
        notificationBuilder.setContentText(notificationMsg);
        notificationBuilder.setSmallIcon(R.drawable.do_or_dare_orange_img);
        notificationBuilder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1, notificationBuilder.build());
    }
}
