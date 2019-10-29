package com.rnjt.eros.api.base;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class Request<T> {

    private Call<T> mCall;
    private RequestDelegate<T> mRequestDelegate;

    public Request(RequestDelegate<T> requestDelegate) {
        mRequestDelegate = requestDelegate;
    }

    protected Call<T> getCall() {
        return mCall;
    }

    public boolean isCanceled() {
        return getCall().isCanceled();
    }

    protected RequestDelegate<T> getRequestDelegate() {
        return mRequestDelegate;
    }

    public void execute() {
        mCall = generateApiCall();
        enqueueCall();
    }

    protected abstract Call<T> generateApiCall();

    public void cancel() {
        if (mCall != null && !mCall.isCanceled()) {
            mCall.cancel();
        }
    }

    private void enqueueCall() {
        mCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                ErrorResponseData errorResponse = null;
                errorResponse = new ErrorResponseData();


                if (response.isSuccessful()) {
                    onSuccess();
                    mRequestDelegate.onSuccess(response.body());
                } else {
                    onError();
                    try {

                        mRequestDelegate.onError(errorResponse);
                        return;

                    } catch (Exception e) {
                        e.printStackTrace();
                        errorResponse.setMessage("Something went wrong. Please try again later.");
                        errorResponse.setResponseCode(response.code());
                        errorResponse.setIsSuccess(0);
                        mRequestDelegate.onError(errorResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onFailed();
                mRequestDelegate.onFailed(t);
            }
        });
    }



    protected void onSuccess() {
    }

    protected void onError() {
    }

    protected void onFailed() {
    }

    public interface RequestDelegate<T> {
        void onSuccess(T result);

        void onError(ErrorResponseData errorResponse);

        void onFailed(Throwable t);
    }
}
