package com.rnjt.eros.base;

import android.support.annotation.StringRes;

public interface MvpView {

    boolean isNetworkAvailable();

    boolean isNetworkAvailable(boolean showMsg);

    void hideLoadingIndicator();

    void showLoadingIndicator();

    void showLoadingIndicator(@StringRes int msgResId);

    void showLoadingIndicator(String loadingMsg);

}
