package com.barmej.wecare.data.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.barmej.wecare.data.DailyNotification;

@Database(entities = {DailyNotification.class}, version = 31, exportSchema = false)
public abstract class DayNotificationDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static DayNotificationDatabase instance;

    public abstract DayNotificationDao dayNotificationDao();

    public static DayNotificationDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            DayNotificationDatabase.class, "daily_notification_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private DayNotificationDao dayNotificationDao;

        private PopulateDbAsyncTask(DayNotificationDatabase database) {
            dayNotificationDao = database.dayNotificationDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dayNotificationDao.addDayNotificationNumber(new DailyNotification("01-01-2020", 0));
            return null;
        }
    }

}
