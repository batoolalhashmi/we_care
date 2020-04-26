package com.barmej.wecare.startnotification;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class StartNotificationIntentService extends IntentService {
    public StartNotificationIntentService() {
        super(StartNotificationIntentService.class.getName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }

}