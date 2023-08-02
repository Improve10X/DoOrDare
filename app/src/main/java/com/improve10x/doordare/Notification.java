package com.improve10x.doordare;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.improve10x.doordare.pendingfragment.PendingFragment;

public class Notification {

    public static void pushNotification(Context context, String notificationTitle, String notificationMsg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("Do/Dare", "Do/Dare", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(context, NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(context, context.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder
                (context, "Do/Dare");
        notificationBuilder.setContentTitle(notificationTitle);
        notificationBuilder.setContentText(notificationMsg);
        notificationBuilder.setSmallIcon(R.drawable.doorg);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1, notificationBuilder.build());
    }
}
