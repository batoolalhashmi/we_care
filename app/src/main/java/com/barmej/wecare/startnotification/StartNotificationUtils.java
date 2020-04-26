package com.barmej.wecare.startnotification;

import android.content.Context;
import android.content.Intent;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class StartNotificationUtils {
    public static void startNotification(Context context) {
        Intent intent = new Intent(context, StartNotificationIntentService.class);
        context.startService(intent);
    }

    public static void scheduleNotification(Context context) {
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(StartNotificationWorker.class, 25, TimeUnit.MINUTES)
                .setInitialDelay(25, TimeUnit.MINUTES)
                .build();
        WorkManager workManager = WorkManager.getInstance(context);
        workManager.enqueueUniquePeriodicWork("startNotificationWorker", ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);
    }
}
