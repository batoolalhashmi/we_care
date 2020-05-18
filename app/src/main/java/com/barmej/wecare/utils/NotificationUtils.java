package com.barmej.wecare.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.barmej.wecare.MainActivity;
import com.barmej.wecare.R;

import java.util.Random;

public class NotificationUtils {
    private static final String NOTIFICATION_CHANNEL_ID = "notification";
    private static final int NOTIFICATION_ID = 1;
    public static final int FOREGROUND_NOTIFICATION_SERVICE = 2;


    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.notification);
            String description = context.getString(R.string.notification_channel_description);
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void showNotification(Context context) {

        String notificationTitle = context.getString(R.string.app_name);
        int smallIcon = R.drawable.ic_launcher_foreground;

        String[] SUGGESTIONS = context.getResources().getStringArray(R.array.suggestions);
        Random random = new Random();
        int randomSuggestionIndex = random.nextInt(SUGGESTIONS.length);
        String notificationText = SUGGESTIONS[randomSuggestionIndex];

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        notificationBuilder.setSmallIcon(smallIcon);
        notificationBuilder.setContentTitle(notificationTitle);
        notificationBuilder.setContentText(notificationText);
        notificationBuilder.setAutoCancel(true);

        Intent intent = new Intent(context, MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntentWithParentStack(intent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);

        Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public static Notification getNotification(Context context) {
        String notificationTitle = context.getString(R.string.app_name);
        int smallIcon = R.drawable.ic_launcher_foreground;
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setOngoing(true)
                .setContentTitle(notificationTitle)
                .setContentText(context.getString(R.string.notification_message))
                .setSmallIcon(smallIcon);
        return notification.build();
    }
}
