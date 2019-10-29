package com.rnjt.eros.ui.SplashScreen;

import android.content.Intent;
import android.os.Handler;

import com.rnjt.eros.R;
import com.rnjt.eros.base.BaseActivity;
import com.rnjt.eros.base.BasePresenter;
import com.rnjt.eros.base.MvpPresenter;
import com.rnjt.eros.ui.MainScreen.HomeActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void setUpViews() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,
                        HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }

    @Override
    protected MvpPresenter createPresenter() {
        return new BasePresenter.AppPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }
}
