package com.barmej.wecare.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.barmej.wecare.data.database.DayNotificationDao;
import com.barmej.wecare.data.database.DayNotificationDatabase;
import com.barmej.wecare.utils.AppExecutor;

import java.util.List;

public class NotificationRepository {
    private static final Object LOCK = new Object();
    private static NotificationRepository sInstance;
    private LiveData<DailyNotification> getSelectedData;
    private LiveData<List<String>> getDates;
    private LiveData<List<Integer>> getNumberOfNotifications;
    private DayNotificationDao dayNotificationDao;
    private DayNotificationDatabase database;
    private AppExecutor mAppExecutor;

    public static NotificationRepository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null)
                    sInstance = new NotificationRepository(context.getApplicationContext());
            }
        }
        return sInstance;
    }

    public NotificationRepository(Context context) {
        database = DayNotificationDatabase.getInstance(context);
        dayNotificationDao = database.dayNotificationDao();
        getDates = dayNotificationDao.getDates();
        mAppExecutor = AppExecutor.getInstance();
    }

    public void insert(final DailyNotification dailyNotification) {
        mAppExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.dayNotificationDao().addDayNotificationNumber(dailyNotification);
            }
        });
    }

    public void update(final DailyNotification dailyNotification) {
        mAppExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.dayNotificationDao().updateDayNotificationNumber(dailyNotification);
            }
        });
    }

    public LiveData<DailyNotification> getSelectedDay(String date) {
        getSelectedData = dayNotificationDao.getSelectedDay(date);
        return getSelectedData;
    }

    public LiveData<List<String>> getDates() {
        return getDates;
    }

    public DailyNotification getDailyNotification(String date) {
        DailyNotification getDailyNotification = dayNotificationDao.getDailyNotifications(date);
        return getDailyNotification;
    }

    public LiveData<List<Integer>> getNumberOfNotifications(String currentDate, String dateBeforeWeek) {
        getNumberOfNotifications = dayNotificationDao.getNumberOfNotifications(currentDate, dateBeforeWeek);
        return getNumberOfNotifications;
    }
}