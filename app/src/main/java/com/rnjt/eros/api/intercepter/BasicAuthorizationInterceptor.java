package com.rnjt.eros.api.intercepter;

import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthorizationInterceptor implements Interceptor {

    private String mName, mPassword;

    public BasicAuthorizationInterceptor(@NonNull String name, @NonNull String password) {
        mName = name;
        mPassword = password;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String credentials = mName + ":" + mPassword;
        String basicAuth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", basicAuth)
                .header("Content-Type", "application/json")
                .method(original.method(), original.body());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}