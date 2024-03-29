package com.rnjt.eros.ui.MainScreen;


import com.rnjt.eros.api.model.Movies;
import com.rnjt.eros.base.BaseView;

public class MoviesContract {

    public interface View extends BaseView {
        void showMoviesResponse(Movies movie);
        void setIsLoading(boolean isLoading);
    }

    public interface Presenter{
        void getMovies(int PageId);
    }

}
