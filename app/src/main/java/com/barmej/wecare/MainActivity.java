package com.barmej.wecare;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.barmej.wecare.data.DailyNotification;
import com.barmej.wecare.databinding.ActivityMainBinding;
import com.barmej.wecare.notificationservice.BootService;
import com.barmej.wecare.utils.LocaleHelper;
import com.barmej.wecare.utils.NotificationUtils;
import com.barmej.wecare.viewmodel.DayNotificationViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final int WEEK_DAY = 7;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Button dailyData = findViewById(R.id.daily_data);

        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        final String currentDate = simpleDateFormat.format(new Date());
        calender.add(Calendar.DAY_OF_YEAR, + 1 );
        final String tomorrow = simpleDateFormat.format(calender.getTime());
        calender.add(Calendar.DAY_OF_YEAR, -7);
        final String dateBeforeWeek = simpleDateFormat.format(calender.getTime());

        DayNotificationViewModel dayNotificationViewModel = ViewModelProviders.of(this).get(DayNotificationViewModel.class);
        dayNotificationViewModel.getSelectedDay(currentDate).observe(this, new Observer<DailyNotification>() {
            @Override
            public void onChanged(DailyNotification dailyNotification) {
                binding.setDailyNotification(dailyNotification);
            }
        });
        dayNotificationViewModel.getNumberOfNotifications(dateBeforeWeek,tomorrow).observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> notifications) {
                int sum = 0;
                   for(int i = 0; i < notifications.size() ; i++) {
                        sum += notifications.get(i);
                    }
                int average = sum / WEEK_DAY;
                    String stringAverage = String.valueOf(average);
                    binding.weeklyAverage.setText(stringAverage);
                }

        });
        dailyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DailyDataActivity.class);
                startActivity(intent);
            }
        });
        NotificationUtils.createNotificationChannel(this);
        startForegroundService(new Intent(this, BootService.class));


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_chang_lang) {
            showLanguageDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showLanguageDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.change_lang_text)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String language = "ar";
                        switch (which) {
                            case 0:
                                language = "ar";
                                break;
                            case 1:
                                language = "en";
                                break;
                        }
                        LocaleHelper.setLocale(MainActivity.this, language);
                        recreate();
                    }
                }).create();
        alertDialog.show();
    }
}
