package com.barmej.wecare.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.barmej.wecare.data.DailyNotification;
import com.barmej.wecare.data.NotificationRepository;

import java.util.List;

public class DayNotificationViewModel extends AndroidViewModel {
    private NotificationRepository repository;
    private LiveData<DailyNotification> getSelectedData;
    private LiveData<List<String>> getDate;
    private LiveData<List<Integer>> getAllData;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DayNotificationViewModel(@NonNull Application application) {
        super(application);
        repository = NotificationRepository.getInstance(application);
        getDate = repository.getDate();
    }

    public LiveData<DailyNotification> getSelectedDay(String date) {
        getSelectedData = repository.getSelectedDay(date);
        return getSelectedData;
    }

    public LiveData<List<String>> getDate() {
        return getDate;
    }

    public DailyNotification getDailyNotification(String date) {
        DailyNotification getDailyNotification = repository.getDailyNotification(date);
        return getDailyNotification;
    }

    public LiveData<List<Integer>> getAllData() {
        getAllData = repository.getAllData();
        return getAllData;
    }

}