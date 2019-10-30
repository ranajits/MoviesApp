package com.rnjt.eros.ui.MainScreen;

import android.app.SearchManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;

import com.rnjt.eros.R;
import com.rnjt.eros.base.BaseActivity;
import com.rnjt.eros.base.BasePresenter;
import com.rnjt.eros.base.LogUtils;
import com.rnjt.eros.base.MvpPresenter;
import com.rnjt.eros.ui.ErosApplication;
import com.rnjt.eros.ui.MainScreen.adapter.ViewPagerAdapter;

import java.lang.reflect.Field;

public class HomeActivity extends BaseActivity {
    private Toolbar toolbar;

    SearchView searchView = null;
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void setUpViews() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        makeActionOverflowMenuShown();

        viewPager = findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    protected MvpPresenter createPresenter() {
        return new BasePresenter.AppPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_homescreen;
    }

    private void makeActionOverflowMenuShown() {
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            Log.d("", e.getLocalizedMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_icon_text_tabs, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        if (mSearchMenuItem != null) {
            searchView = (SearchView) mSearchMenuItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this
                    .getComponentName()));
        }
        searchView.setIconifiedByDefault(true);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String query) {
                Log.d("DD 01", "" + query);
                if (viewPager.getCurrentItem() != 0)
                    viewPager.setCurrentItem(0);
                if (ErosApplication.getMoviesFragment() != null)
                    ErosApplication.getMoviesFragment().beginSearch(query);

                return false;

            }

            public boolean onQueryTextSubmit(String query) {
                // Here u can get the value "query" which is entered in the

                return false;

            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    LogUtils.logE("hasFocus");
                    ErosApplication.setPagination = true;

                } else {
                    ErosApplication.setPagination = false;

                    LogUtils.logE("hasFocus no");
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ErosApplication.movieArrayList = null;
    }
}
