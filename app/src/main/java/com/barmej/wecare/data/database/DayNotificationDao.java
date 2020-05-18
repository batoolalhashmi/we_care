package com.barmej.wecare.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.barmej.wecare.data.DailyNotification;

import java.util.List;

@Dao
public interface DayNotificationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addDayNotificationNumber(DailyNotification dailyNotification);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDayNotificationNumber(DailyNotification dailyNotification);

    @Query("SELECT * FROM daily_notification WHERE date = :selectedDate ")
    LiveData<DailyNotification> getSelectedDay(String selectedDate);

    @Query("SELECT DATE FROM daily_notification")
    LiveData<List<String>> getDates();

    @Query("SELECT * FROM daily_notification WHERE date = :selectedDate")
    DailyNotification day (String selectedDate);

    @Query("SELECT NUMBER_OF_NOTIFICATION FROM daily_notification WHERE date BETWEEN :currentDate AND :dateBeforeWeek")
    LiveData<List<Integer>> getNumberOfNotifications(String currentDate , String dateBeforeWeek);

}
