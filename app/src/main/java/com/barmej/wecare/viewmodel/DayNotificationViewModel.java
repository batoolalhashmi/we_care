package com.barmej.wecare.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.barmej.wecare.data.DailyNotification;
import com.barmej.wecare.data.NotificationRepository;

import java.util.List;

public class DayNotificationViewModel extends AndroidViewModel {
    private NotificationRepository repository;
    private LiveData<DailyNotification> getSelectedData;
    private LiveData<List<String>> getDates;
    private LiveData<List<Integer>> getNumberOfNotifications;


    public DayNotificationViewModel(@NonNull Application application) {
        super(application);
        repository = NotificationRepository.getInstance(application);
        getDates = repository.getDates();

    }

    public LiveData<DailyNotification> getSelectedDay(String date) {
        getSelectedData = repository.getSelectedDay(date);
        return getSelectedData;
    }

    public LiveData<List<String>> getDates() {
        return getDates;
    }

    public DailyNotification getDailyNotification(String date) {
        DailyNotification getDailyNotification = repository.getDailyNotification(date);
        return getDailyNotification;
    }

    public LiveData<List<Integer>> getNumberOfNotifications(String currentDate, String dateBeforeWeek) {
        getNumberOfNotifications = repository.getNumberOfNotifications(currentDate, dateBeforeWeek);
        return getNumberOfNotifications;
    }

}