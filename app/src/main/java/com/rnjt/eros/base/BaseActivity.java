package com.rnjt.eros.base;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.http.HttpResponseCache;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.rnjt.eros.R;

import java.util.Locale;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends MvpPresenter> extends AppCompatActivity implements MvpView {

    // Showing & hiding of ProgressDialog
    private ProgressDialog mProgressDialog;
    // MvpPreseneter attach & detach
    private P mPresenter;
    // Toolbar setup
    private Toolbar mToolbar;
    private TextView mToolbarTitleTextView;
    private TextView mToolbarsubTitleTextView;
    private ImageView mToolbarBrandingImageView;

    protected void showProgressLoader(@StringRes int resId) {
        showProgressLoader(getString(resId));
    }

    protected void showProgressLoader(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void hideProgressLoader() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void showSnackBar(View view, String message, boolean longDuration) {
        Snackbar.make(view, message, longDuration ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
    }

    protected void showSnackBar(View view, @StringRes int resId, boolean longDuration) {
        showSnackBar(view, getString(resId), longDuration);
    }

    protected void showToast(@StringRes int resId, boolean longDuration) {
        showToast(getString(resId), longDuration);
    }

    protected void showToast(String message, boolean longDuration) {
        Toast.makeText(this, message, longDuration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    protected abstract void setUpViews();

    protected abstract P createPresenter();

    protected abstract int getLayout();

    protected P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayout());
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        mPresenter.attachView(this);
        setUI();
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        cleareHttpCash();
        super.onDestroy();
    }

    protected void setUI() {
        ButterKnife.bind(this);
        setUpViews();
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    protected void setToolbar(Toolbar toolbar) {
  /*      mToolbarBrandingImageView = (ImageView) findViewById(R.id.img_branding);
        if (mToolbarBrandingImageView != null) {
            mToolbarBrandingImageView.setVisibility(View.GONE);
        }
        setSupportActionBar(toolbar);*/
    }

    protected void setToolbarwithBack(Toolbar toolbar) {
        /*mToolbarBrandingImageView = (ImageView) findViewById(R.id.img_branding);
        if (mToolbarBrandingImageView != null) {
            mToolbarBrandingImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

        }
        setSupportActionBar(toolbar);*/
    }

    protected void setToolbarTitle(@StringRes int titleResId) {
        setToolbarTitle(getString(titleResId));
    }

    protected void setToolbarTitle(String title) {
       /* getSupportActionBar().setTitle("");
        mToolbarTitleTextView = (TextView) findViewById(R.id.toolbar_title);
        if (mToolbarTitleTextView != null && title != null) {
            mToolbarTitleTextView.setText(title);
        }*/
    }
    protected String  getToobarTitle() {

        if (mToolbarTitleTextView != null) {
            return  mToolbarTitleTextView.getText().toString();
        }
        return "";
    }

    protected void setToolbarsubTitle(String title) {

    }

    protected void setToolbarTitle(String title, boolean isIconDisplay) {
        getSupportActionBar().setTitle("");
        if (isIconDisplay) {
            mToolbarTitleTextView.setVisibility(View.GONE);
            mToolbarBrandingImageView.setVisibility(View.VISIBLE);
        } else {
            mToolbarBrandingImageView.setVisibility(View.GONE);
            mToolbarTitleTextView.setVisibility(View.VISIBLE);
            if (mToolbarTitleTextView != null && title != null) {
                mToolbarTitleTextView.setText(title);
            }
        }
    }


    protected void setUpBackNavigation(@StringRes int titleResId) {
        setUpBackNavigation(getString(titleResId));
    }

    protected void setUpBackNavigation(String title) {
        setToolbarTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    protected void setUpBackNavigationWithsubtitle(String title, String subtitle) {
        setToolbarTitle(title);
        setToolbarsubTitle(subtitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    protected void setUpBackNavigationOnly() {
        setToolbarTitle("");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    // MvpView Methods
    @Override
    public boolean isNetworkAvailable() {
        return NetworkUtil.isNetworkAvailable(this, false);
    }

    // MvpView Methods
    @Override
    public boolean isNetworkAvailable(boolean showMsg) {
        return NetworkUtil.isNetworkAvailable(this, true);
    }

    @Override
    public void showLoadingIndicator() {
        showLoadingIndicator(R.string.text_common_loading);
    }

    @Override
    public void showLoadingIndicator(@StringRes int msgResId) {
        showLoadingIndicator(getString(msgResId));
    }

    @Override
    public void showLoadingIndicator(String loadingMsg) {
        showProgressLoader(loadingMsg);
    }

    @Override
    public void hideLoadingIndicator() {
        hideProgressLoader();
    }

    // Screen Related Functions
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                ViewUtil.hideKeyboard(this);
            }
        } catch (Exception e) {
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ViewUtil.hideKeyboard(this);
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void cleareHttpCash() {

        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if (cache != null) {
            cache.flush();
        }
    }

    public void circleReveal(int viewId, int posFromRight, Boolean containsOverflow, Boolean isShow) {
        View myView = findViewById(viewId);

        int width = myView.getWidth();

        if (posFromRight > 0) {
            width -= (posFromRight * getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)) - (getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2);
        }

        if (containsOverflow) {
            width -= getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);
        }

        int cx = width;
        int cy = myView.getHeight() / 2;

        Animator anim = null;
        if (isShow)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, (float) width);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float) width, 0f);
            }

        anim.setDuration(220L);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    myView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        // make the view visible and start the animation
        if (isShow) {
            myView.setVisibility(View.VISIBLE);
        }

        // start the animation
        anim.start();


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public boolean isAlive() {
        return !isFinishing() && !isDestroyed();
    }

}
