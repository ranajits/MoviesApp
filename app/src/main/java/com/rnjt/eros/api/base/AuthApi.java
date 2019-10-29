package com.rnjt.eros.api.base;


import com.rnjt.eros.api.model.MovieDetail.MovieDetail;
import com.rnjt.eros.api.model.Movies;
import com.rnjt.eros.api.model.UserInfo;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface AuthApi {

    @GET("users")
    Call<ArrayList<UserInfo>> getUsers();

    @GET("movie/top_rated")
    Call<Movies> getMovies(@QueryMap Map<String, String> options);

    @GET("movie/{movieId}")
    Call<MovieDetail> getMovieDetails(@Path("movieId") String movieId, @QueryMap Map<String, String> options);

}
