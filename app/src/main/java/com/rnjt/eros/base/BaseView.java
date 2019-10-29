package com.rnjt.eros.base;

import android.support.annotation.StringRes;

public interface BaseView extends MvpView {

    void showMessage(String message);

    void showMessage(@StringRes int resId);

}
