package com.rnjt.eros.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rnjt.eros.BuildConfig;
import com.rnjt.eros.api.gson.DateDeserialize;
import com.rnjt.eros.api.gson.DateUtc;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

   // private static final String API_BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String API_BASE_URL = "https://api.themoviedb.org/3/";

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static GsonBuilder gsonBuilder = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(new TypeToken<DateUtc>() {
            }.getType(), new DateDeserialize());
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(getApiBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.setLenient().create()));

    public static String getApiBaseUrl() {
        return API_BASE_URL;
    }

    private static Retrofit getRetrofit(Interceptor interceptor) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        // NOTE : increase the connect & read timeout from default 10s
        // getting socketTimeOut creates multiple comment if posting back the comment
        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(45, TimeUnit.SECONDS);

        if (interceptor != null) {
            httpClientBuilder.addInterceptor(interceptor);
        } else {
            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Content-Type", "application/json");

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(logging);
        }

        Retrofit retrofit = builder
                .client(httpClientBuilder.build())
                .build();

        return retrofit;
    }

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = getRetrofit(null);
        return retrofit.create(serviceClass);
    }


}
