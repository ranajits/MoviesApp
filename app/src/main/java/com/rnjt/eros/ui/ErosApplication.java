package com.rnjt.eros.ui;


import android.app.Application;

import com.rnjt.eros.api.model.Movie;
import com.rnjt.eros.api.model.UserInfo;

import java.util.ArrayList;

public class ErosApplication extends Application {
    private static ErosApplication instance;

    public static ArrayList<UserInfo> userInfoArrayList= new ArrayList<>();
    public static ArrayList<Movie> movieArrayList= new ArrayList<>();

    public static ArrayList<UserInfo> getUserInfoArrayList() {
        return userInfoArrayList;
    }

    public static void setUserInfoArrayList(ArrayList<UserInfo> userInfoArrayList) {
        ErosApplication.userInfoArrayList = userInfoArrayList;
    }

    public static ErosApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
