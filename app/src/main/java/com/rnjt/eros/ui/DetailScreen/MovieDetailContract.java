package com.rnjt.eros.ui.DetailScreen;


import com.rnjt.eros.api.model.MovieDetail.MovieDetail;
import com.rnjt.eros.api.model.Movies;
import com.rnjt.eros.api.model.UserInfo;
import com.rnjt.eros.base.BaseView;

import java.util.ArrayList;

public class MovieDetailContract {

    public interface View extends BaseView {
        void showMovieDetailResponse(MovieDetail movie);
        void setIsLoading(boolean isLoading);
    }

    public interface Presenter{
        void getMovieDetails(String movieId);
    }

}
