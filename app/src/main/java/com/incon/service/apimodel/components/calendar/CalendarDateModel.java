package com.incon.service.apimodel.components.calendar;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 8/12/16.
 *
 */
public class CalendarDateModel implements Parcelable {
    private int dayOfMonth;
    private String dayOfWeek;
    private String nameOfMonth;
    private String year;

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getNameOfMonth() {
        return nameOfMonth;
    }

    public void setNameOfMonth(String nameOfMonth) {
        this.nameOfMonth = nameOfMonth;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public CalendarDateModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(dayOfMonth);
        parcel.writeString(dayOfWeek);
        parcel.writeString(nameOfMonth);
        parcel.writeString(year);
    }

    public CalendarDateModel(Parcel in) {
        dayOfMonth = in.readInt();
        dayOfWeek = in.readString();
        nameOfMonth = in.readString();
        year = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Creator() {
        @Override
        public CalendarDateModel createFromParcel(Parcel parcel) {
            return new CalendarDateModel(parcel);
        }

        @Override
        public CalendarDateModel[] newArray(int i) {
            return new CalendarDateModel[i];
        }
    };

    @Override
    public boolean equals(Object calendarDateModel) {
        if (calendarDateModel instanceof CalendarDateModel) {
            CalendarDateModel calendarDateModel1 = ((CalendarDateModel) calendarDateModel);
            if (calendarDateModel1 != null) {
                return  (calendarDateModel1.dayOfMonth == dayOfMonth
                        && calendarDateModel1.nameOfMonth.equalsIgnoreCase(nameOfMonth)
                        && calendarDateModel1.getYear().equalsIgnoreCase(year));

            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
