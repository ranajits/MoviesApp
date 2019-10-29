package com.rnjt.eros.base;

import android.app.ProgressDialog;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;


import com.rnjt.eros.R;

import butterknife.ButterKnife;

public abstract class BaseFragment<P extends MvpPresenter> extends Fragment implements MvpView {

    // Showing & hiding of ProgressDialog
    private ProgressDialog mProgressDialog;
    // MvpPreseneter attach & detach
    private P mPresenter;


    protected void showProgressLoader(@StringRes int resId) {
        showProgressLoader(getResources().getString(resId));
    }

    protected void showProgressLoader(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
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
        Toast.makeText(getActivity(), message, longDuration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    protected abstract void setUpViews();

    protected abstract P createPresenter();

    protected P getPresenter() {
        return mPresenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        mPresenter.attachView(this);
        setUpViews();
    }

    protected void setUI() {
        setUpViews();
    }


    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    // MvpView Methods
    @Override
    public boolean isNetworkAvailable() {
        return NetworkUtil.isNetworkAvailable(getActivity(), false);
    }

    @Override
    public boolean isNetworkAvailable(boolean showMsg) {
        return NetworkUtil.isNetworkAvailable(getActivity(), true);
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

    // EventBus Events
    public void showMessageEvent(String message) {

    }

    @Override
    public void onDetach() {

        cleareHttpCash();
        super.onDetach();
    }

    @Override
    public void onDestroy() {

        cleareHttpCash();
        super.onDestroy();
    }

    private void cleareHttpCash() {

        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if (cache != null) {
            cache.flush();
        }
    }

}
