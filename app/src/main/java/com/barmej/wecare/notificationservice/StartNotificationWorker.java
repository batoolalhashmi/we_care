package com.barmej.wecare.notificationservice;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.barmej.wecare.data.DailyNotification;
import com.barmej.wecare.data.NotificationRepository;
import com.barmej.wecare.utils.AppExecutor;
import com.barmej.wecare.utils.NotificationUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StartNotificationWorker extends Worker {
    public StartNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        AppExecutor.getInstance().getMainThread().execute(new Runnable() {
            @Override
            public void run() {
                Calendar calender = Calendar.getInstance();
                calender.setTime(new Date());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                final String currentDate = simpleDateFormat.format(calender.getTime());
                final NotificationRepository notificationRepository = NotificationRepository.getInstance(getApplicationContext());
                final LiveData<DailyNotification> dayLiveData = notificationRepository.getSelectedDay(currentDate);
                dayLiveData.observeForever(new Observer<DailyNotification>() {
                    @Override
                    public void onChanged(DailyNotification dailyNotification) {
                        if (dailyNotification == null) {
                            notificationRepository.insert(new DailyNotification(currentDate, 1));
                        } else {
                            dailyNotification.setNumberOfNotifications(dailyNotification.getNumberOfNotifications() + 1);
                            notificationRepository.update(dailyNotification);
                        }
                        dayLiveData.removeObserver(this);
                    }
                });
                NotificationUtils.showNotification(getApplicationContext());
            }
        });
        return Result.success();
    }
}
