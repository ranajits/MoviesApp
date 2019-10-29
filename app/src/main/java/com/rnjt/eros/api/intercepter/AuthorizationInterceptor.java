package com.rnjt.eros.api.intercepter;

import android.support.annotation.NonNull;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {

    private String mToken;

    public AuthorizationInterceptor(@NonNull String token) {
        mToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String authToken = mToken;
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder();
        requestBuilder.header("Content-Type", "application/json");

        requestBuilder.method(original.method(), original.body());
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

}
