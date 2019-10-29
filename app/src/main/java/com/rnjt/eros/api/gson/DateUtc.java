package com.rnjt.eros.api.gson;

import android.os.Parcel;
import android.os.Parcelable;

import com.rnjt.eros.base.AppConfig;
import com.rnjt.eros.base.DateUtil;

import java.util.Date;

public class DateUtc implements Parcelable {

    public static final Creator<DateUtc> CREATOR = new Creator<DateUtc>() {
        @Override
        public DateUtc createFromParcel(Parcel in) {
            return new DateUtc(in);
        }

        @Override
        public DateUtc[] newArray(int size) {
            return new DateUtc[size];
        }
    };
    private static final String DATE_FORMAT = AppConfig.CREATED_DATE_FORMAT;
    private String dateAsString;
    private Date date;

    public DateUtc(String dateAsString) {
        this.dateAsString = dateAsString;
        date = DateUtil.convertUTCtoLocalDate(dateAsString, DATE_FORMAT);
    }

    protected DateUtc(Parcel in) {
        dateAsString = in.readString();
        date = DateUtil.convertUTCtoLocalDate(dateAsString, DATE_FORMAT);
    }

    public String getDateAsString() {
        return dateAsString;
    }

    public void setDateAsString(String dateAsString) {
        this.dateAsString = dateAsString;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dateAsString);
    }
}
