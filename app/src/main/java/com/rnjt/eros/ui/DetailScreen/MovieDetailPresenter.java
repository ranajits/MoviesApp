package com.rnjt.eros.ui.DetailScreen;

import com.rnjt.eros.api.base.ApiRequestHandler;
import com.rnjt.eros.api.base.ErrorResponseData;
import com.rnjt.eros.api.base.Request;
import com.rnjt.eros.api.model.MovieDetail.MovieDetail;
import com.rnjt.eros.base.BasePresenter;

public class MovieDetailPresenter extends BasePresenter<MovieDetailContract.View> implements MovieDetailContract.Presenter {
    private static final String TAG = "MovieDetailPresenter";

    @Override
    public void getMovieDetails(String movieId) {

        ApiRequestHandler.getMovieDetail(movieId, new Request.RequestDelegate<MovieDetail>() {
            @Override
            public void onSuccess(MovieDetail result) {
                getView().showMovieDetailResponse(result);
            }

            @Override
            public void onError(ErrorResponseData errorResponse) {
                getView().showMessage("Something went wrong !");
            }

            @Override
            public void onFailed(Throwable t) {
                getView().showMessage("Something went wrong !");
                getView().setIsLoading(false);
            }
        });
    }
}
