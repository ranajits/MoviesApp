package com.rnjt.eros.ui.MainScreen;

import com.rnjt.eros.api.base.ApiRequestHandler;
import com.rnjt.eros.api.base.ErrorResponseData;
import com.rnjt.eros.api.base.Request;
import com.rnjt.eros.api.model.Movies;
import com.rnjt.eros.base.BasePresenter;

public class MoviesPresenter extends BasePresenter<MoviesContract.View> implements MoviesContract.Presenter {
    private static final String TAG = "MoviesPresenter";

    @Override
    public void getMovies(int pageId) {
        getView().setIsLoading(true);

        ApiRequestHandler.getMovies(pageId, new Request.RequestDelegate<Movies>() {
            @Override
            public void onSuccess(Movies result) {
                getView().showMoviesResponse(result);
                getView().setIsLoading(false);

            }

            @Override
            public void onError(ErrorResponseData errorResponse) {
                getView().showMessage("Something went wrong !");
                getView().setIsLoading(false);

            }

            @Override
            public void onFailed(Throwable t) {
                getView().showMessage("Something went wrong !");
                getView().setIsLoading(false);

            }
        });
    }
}
