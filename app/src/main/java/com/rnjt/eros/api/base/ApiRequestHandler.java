package com.rnjt.eros.api.base;

import android.support.annotation.NonNull;

import com.rnjt.eros.api.model.MovieDetail.MovieDetail;
import com.rnjt.eros.api.model.Movies;
import com.rnjt.eros.api.model.UserInfo;
import com.rnjt.eros.api.request.movieDetail.getMovieDetailRequest;
import com.rnjt.eros.api.request.movies.getMoviesRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiRequestHandler {


    public static getMoviesRequest getMovies(int pageId, @NonNull Request.RequestDelegate<Movies> requestDelegate) {
        //@GET("top_rated?api_key=3f8057259f642ebae7f81cee429a6d11&language=en-US&page=1")

        Map<String, String> data = new HashMap<>();
        data.put("api_key", "3f8057259f642ebae7f81cee429a6d11");
        data.put("language", "en-US");
        data.put("page", String.valueOf(pageId));

        getMoviesRequest request = new getMoviesRequest(data, requestDelegate);
        request.execute();
        return request;
    }

    public static getMovieDetailRequest getMovieDetail(String movieId, @NonNull Request.RequestDelegate<MovieDetail> requestDelegate) {
        Map<String, String> data = new HashMap<>();
        data.put("api_key", "3f8057259f642ebae7f81cee429a6d11");
        data.put("language", "en-US");

        getMovieDetailRequest request = new getMovieDetailRequest(movieId, data, requestDelegate);
        request.execute();
        return request;
    }

}
