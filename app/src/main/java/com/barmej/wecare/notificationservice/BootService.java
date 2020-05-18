package com.barmej.wecare.notificationservice;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.barmej.wecare.utils.NotificationUtils;

public class BootService extends Service {

    BroadcastReceiver screenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                StartNotificationUtils.StopScheduleNotification(BootService.this);

            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                StartNotificationUtils.scheduleNotification(BootService.this);

            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        StartNotificationUtils.scheduleNotification(BootService.this);
        Notification notification = NotificationUtils.getNotification(this);
        startForeground(NotificationUtils.FOREGROUND_NOTIFICATION_SERVICE, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        registerReceiver(screenReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        registerReceiver(screenReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(screenReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
