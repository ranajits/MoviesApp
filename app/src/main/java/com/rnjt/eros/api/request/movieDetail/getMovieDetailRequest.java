package com.rnjt.eros.api.request.movieDetail;

import com.rnjt.eros.api.ApiClient;
import com.rnjt.eros.api.base.AuthApi;
import com.rnjt.eros.api.base.Request;
import com.rnjt.eros.api.model.MovieDetail.MovieDetail;
import com.rnjt.eros.api.model.Movies;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class getMovieDetailRequest extends Request<MovieDetail> {

    Map<String, String> data = new HashMap<>();
    String movieId;

    public getMovieDetailRequest(String movieId, Map<String, String> data, RequestDelegate<MovieDetail> requestDelegate) {
        super(requestDelegate);
        this.data= data;
        this.movieId= movieId;
    }

    @Override
    protected Call<MovieDetail> generateApiCall() {
        AuthApi authApi = ApiClient.createService(AuthApi.class);
        return authApi.getMovieDetails(movieId, data);
    }
}
