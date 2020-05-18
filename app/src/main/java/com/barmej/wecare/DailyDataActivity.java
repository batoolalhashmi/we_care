package com.barmej.wecare;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.barmej.wecare.data.DailyNotification;
import com.barmej.wecare.data.NotificationRepository;
import com.barmej.wecare.databinding.ActivityDailyDataBinding;
import com.barmej.wecare.viewmodel.DayNotificationViewModel;

import java.util.ArrayList;
import java.util.List;

public class DailyDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private List<String> mDates;
    private Spinner daySpinner;
    private String selectedDate;
    ArrayAdapter<String> dateAdapter;
    DayNotificationViewModel dayNotificationViewModel;
    NotificationRepository notificationRepository;
    ActivityDailyDataBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_daily_data);
        daySpinner = findViewById(R.id.day_spinner);
        notificationRepository = NotificationRepository.getInstance(this);
        dayNotificationViewModel = ViewModelProviders.of(this).get(DayNotificationViewModel.class);
        dayNotificationViewModel.getDates().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> dates) {
                mDates.clear();
                mDates.addAll(dates);
            }
        });
        mDates = new ArrayList<>();
        mDates.add(getString(R.string.select_date));
        dateAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mDates);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dateAdapter);
        daySpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(final AdapterView<?> adapterView, View view, final int position, long l) {
        selectedDate = mDates.get(position);
        if (selectedDate != null) {
            DailyNotification dailyNotification = dayNotificationViewModel.getDailyNotification(selectedDate);
            binding.setDailyNotification(dailyNotification);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
