package com.barmej.wecare.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.barmej.wecare.data.database.DayNotificationDao;
import com.barmej.wecare.data.database.DayNotificationDatabase;

import java.util.List;

public class NotificationRepository {
    private static final Object LOCK = new Object();
    private static NotificationRepository sInstance;
    private LiveData<DailyNotification> getSelectedData;
    private LiveData<List<String>> getDates;
    private LiveData<List<Integer>> getNumberOfNotifications;
    private DayNotificationDao dayNotificationDao;
    private DayNotificationDatabase database;

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
    }


    public void insert(DailyNotification dailyNotification) {

        new insertDayAsyncTask(dayNotificationDao).execute(dailyNotification);
    }

    public void update(DailyNotification dailyNotification) {
        new updateDayAsyncTask(dayNotificationDao).execute(dailyNotification);
    }


    public LiveData<DailyNotification> getSelectedDay(String date) {
        getSelectedData = dayNotificationDao.getSelectedDay(date);
        return getSelectedData;
    }

    public LiveData<List<String>> getDates() {
        return getDates;
    }

    public DailyNotification getDailyNotification(String date) {
        DailyNotification getDailyNotification = dayNotificationDao.day(date);
        return getDailyNotification;
    }

    public LiveData<List<Integer>> getNumberOfNotifications(String currentDate, String dateBeforeWeek) {
        getNumberOfNotifications = dayNotificationDao.getNumberOfNotifications(currentDate , dateBeforeWeek);
        return getNumberOfNotifications;
    }

    private static class insertDayAsyncTask extends AsyncTask<DailyNotification, Void, Void> {
        private DayNotificationDao dayNotificationDao;

        private insertDayAsyncTask(DayNotificationDao dayNotificationDao) {
            this.dayNotificationDao = dayNotificationDao;

        }

        @Override
        protected Void doInBackground(final DailyNotification... dailyNotifications) {
            dayNotificationDao.addDayNotificationNumber(dailyNotifications[0]);
            return null;
        }
    }

    private static class updateDayAsyncTask extends AsyncTask<DailyNotification, Void, Void> {
        private DayNotificationDao dayNotificationDao;

        private updateDayAsyncTask(DayNotificationDao dayNotificationDao) {
            this.dayNotificationDao = dayNotificationDao;
        }

        @Override
        protected Void doInBackground(final DailyNotification... dailyNotifications) {
            dayNotificationDao.updateDayNotificationNumber(dailyNotifications[0]);
            return null;
        }
    }
}