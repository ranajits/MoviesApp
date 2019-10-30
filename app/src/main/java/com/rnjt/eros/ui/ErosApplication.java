package com.rnjt.eros.ui;


import android.app.Application;

import com.rnjt.eros.api.model.Movie;
import com.rnjt.eros.api.model.UserInfo;
import com.rnjt.eros.ui.MainScreen.fragments.MoviesFragment;

import java.util.ArrayList;

public class ErosApplication extends Application {
    public static boolean setPagination;
    private static ErosApplication instance;

    public static ArrayList<UserInfo> userInfoArrayList= new ArrayList<>();
    public static ArrayList<Movie> movieArrayList= new ArrayList<>();
    private static MoviesFragment moviesFragment;

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

    public static MoviesFragment getMoviesFragment() {
        return moviesFragment;
    }

    public static void setMoviesFragment(final MoviesFragment fragment) {

        if (fragment == null) return;

        if (ErosApplication.moviesFragment != null) return;

        ErosApplication.moviesFragment = fragment;

    }

    public static void notifySearchFragmentAdapter() {

        moviesFragment.getAdapter().notifyDataSetChanged();

    }
}
