package com.rnjt.eros.ui.MainScreen.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rnjt.eros.ui.MainScreen.fragments.FavouriteFragment;
import com.rnjt.eros.ui.MainScreen.fragments.MoviesFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new MoviesFragment();
            case 1:
                return new FavouriteFragment();
            default:
                return new MoviesFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "TOP RATED";
            case 1:
                return "Favourites";
            default:
                return "TOP RATED";
        }
    }

    public void beginSearch(String query) {
    }
}