package com.rnjt.eros.api.request.movies;

import com.rnjt.eros.api.ApiClient;
import com.rnjt.eros.api.base.AuthApi;
import com.rnjt.eros.api.base.Request;
import com.rnjt.eros.api.model.Movies;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class getMoviesRequest extends Request<Movies> {

    Map<String, String> data = new HashMap<>();

    public getMoviesRequest(Map<String, String> data, RequestDelegate<Movies> requestDelegate) {
        super(requestDelegate);
        this.data= data;
    }

    @Override
    protected Call<Movies> generateApiCall() {
        AuthApi authApi = ApiClient.createService(AuthApi.class);
        return authApi.getMovies(data);
    }
}
