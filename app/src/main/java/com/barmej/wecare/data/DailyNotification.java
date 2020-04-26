package com.barmej.wecare.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "daily_notification")
public class DailyNotification implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "DATE")
    private String date;
    @ColumnInfo(name = "NUMBER_OF_NOTIFICATION")
    private int numberOfNotifications;

    public DailyNotification(String date, int numberOfNotifications) {
        this.date = date;
        this.numberOfNotifications = numberOfNotifications;
    }

    protected DailyNotification(Parcel in) {
        id = in.readInt();
        date = in.readString();
        numberOfNotifications = in.readInt();
    }

    public static final Creator<DailyNotification> CREATOR = new Creator<DailyNotification>() {
        @Override
        public DailyNotification createFromParcel(Parcel in) {
            return new DailyNotification(in);
        }

        @Override
        public DailyNotification[] newArray(int size) {
            return new DailyNotification[size];
        }
    };

    public int getNumberOfNotifications() {
        return numberOfNotifications;
    }

    public void setNumberOfNotifications(int numberOfNotifications) {
        this.numberOfNotifications = numberOfNotifications;
    }

    @NotNull
    public String getDate() {
        return date;
    }

    public void setDate(@NotNull String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(date);
        parcel.writeInt(numberOfNotifications);
    }
}
