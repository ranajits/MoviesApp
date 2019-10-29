package com.rnjt.eros.ui.MainScreen;

import android.app.SearchManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.rnjt.eros.R;
import com.rnjt.eros.base.BaseActivity;
import com.rnjt.eros.base.BasePresenter;
import com.rnjt.eros.base.MvpPresenter;
import com.rnjt.eros.ui.MainScreen.adapter.ViewPagerAdapter;
import com.rnjt.eros.ui.MainScreen.fragments.FavouriteFragment;
import com.rnjt.eros.ui.MainScreen.fragments.MoviesFragment;

import java.lang.reflect.Field;

public class HomeActivity extends BaseActivity {
    private Toolbar toolbar;

    SearchView searchView = null;
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    MoviesFragment moviesFragment;
    @Override
    protected void setUpViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        makeActionOverflowMenuShown();

        viewPager= findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getApplicationContext(),
                        "Selected page position: " + position,
                        Toast.LENGTH_SHORT).show();
                if (searchView != null && !searchView.isIconified()) {
                    //searchView.onActionViewExpanded();
                    searchView.setIconified(true);
                    searchView.setIconified(true);

                }
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                // Code goes here
                Toast.makeText(getApplicationContext(),
                        "onPageScrolled",
                        Toast.LENGTH_SHORT).show();
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
                Toast.makeText(getApplicationContext(),
                        "onPageScrollStateChanged",
                        Toast.LENGTH_SHORT).show();
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
        SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
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
        //MenuItemCompat.expandActionView(mSearchMenuItem);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String query) {
                Log.d("DD 01",""+ query );

                PagerAdapter pagerAdapter = (PagerAdapter) viewPager
                        .getAdapter();
                for (int i = 0; i < pagerAdapter.getCount(); i++) {

                    Fragment viewPagerFragment = (Fragment) viewPager
                            .getAdapter().instantiateItem(viewPager, i);
                    if (viewPagerFragment != null
                            && viewPagerFragment.isAdded()) {
                        Log.d("DD 1","");

                        if (viewPagerFragment instanceof FavouriteFragment) {

                            Log.d("DD 2","");

                        } else if (viewPagerFragment instanceof MoviesFragment) {
                        moviesFragment = (MoviesFragment) viewPagerFragment;
                            if (moviesFragment != null) {
                                moviesFragment.beginSearch(query);
                                Log.d("DD 3","");

                            }else {
                                Log.d("DD 4","");

                            }
                        }else {
                            Log.d("DD 5","");

                        }
                    }else {
                        Log.d("DD 6","");

                    }
                }

                return false;

            }

            public boolean onQueryTextSubmit(String query) {
                // Here u can get the value "query" which is entered in the

                return false;

            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }
}
