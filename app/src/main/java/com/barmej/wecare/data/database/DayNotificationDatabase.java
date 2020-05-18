package com.barmej.wecare.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.barmej.wecare.data.DailyNotification;

@Database(entities = {DailyNotification.class}, version = 33, exportSchema = false)
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
                            .build();
                }
            }
        }
        return instance;
    }
}
