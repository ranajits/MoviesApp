package com.rnjt.eros.base;

import android.support.annotation.StringRes;

public abstract class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    protected V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    protected boolean isViewAttached() {
        return mView != null;
    }

    protected V getView() {
        return mView;
    }

    public static class AppView implements BaseView {

        @Override
        public void showMessage(String message) {

        }

        @Override
        public void showMessage(@StringRes int resId) {

        }

        @Override
        public boolean isNetworkAvailable() {
            return false;
        }

        @Override
        public boolean isNetworkAvailable(boolean showMsg) {
            return false;
        }

        @Override
        public void hideLoadingIndicator() {

        }

        @Override
        public void showLoadingIndicator() {

        }

        @Override
        public void showLoadingIndicator(@StringRes int msgResId) {

        }

        @Override
        public void showLoadingIndicator(String loadingMsg) {

        }
    }

    public static class AppPresenter extends BasePresenter<AppView> {

    }

}
