package com.rnjt.eros.ui.MainScreen.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.rnjt.eros.R;
import com.rnjt.eros.api.model.Movie;
import com.rnjt.eros.api.model.Movies;
import com.rnjt.eros.base.BaseFragment;
import com.rnjt.eros.base.LogUtils;
import com.rnjt.eros.ui.DetailScreen.MovieDetailActivity;
import com.rnjt.eros.ui.ErosApplication;
import com.rnjt.eros.ui.MainScreen.MoviesContract;
import com.rnjt.eros.ui.MainScreen.MoviesPresenter;
import com.rnjt.eros.ui.MainScreen.fragments.adapter.MoviesAdapter;

import java.util.ArrayList;

import static com.rnjt.eros.utils.Constants.EXTRA_IMAGE;
import static com.rnjt.eros.utils.Constants.EXTRA_MOVIE_ID;
import static com.rnjt.eros.utils.Constants.EXTRA_TITLE;


public class MoviesFragment extends BaseFragment<MoviesPresenter> implements MoviesContract.View {

    View view;
    RecyclerView rvMovies;
    MoviesAdapter moviesAdapter;
    LottieAnimationView lotiView;
    int pageId = 1;
    private GridLayoutManager layoutManager;
    boolean isLoading;
    ArrayList<Movie> movieArrayList;
    ArrayList<Movie> filteredDataList;
    boolean isAttached;
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !ErosApplication.setPagination) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    LogUtils.logE("scroll Loading");
                    getPresenter().getMovies(pageId + 1);
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movies, container, false);
        rvMovies = view.findViewById(R.id.rvMovies);
        lotiView = view.findViewById(R.id.lotiView);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isAttached = true;
    }

    void setmMovieFragment() {
        ErosApplication.setMoviesFragment(this);
    }

    @Override
    protected MoviesPresenter createPresenter() {
        return new MoviesPresenter();
    }

    @Override
    protected void setUpViews() {
        setmMovieFragment();
        layoutManager = new GridLayoutManager(getActivity(), 2);
        rvMovies.setLayoutManager(layoutManager);

        rvMovies.setHasFixedSize(true);
        rvMovies.addOnScrollListener(recyclerViewOnScrollListener);
        movieArrayList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(getActivity());
        rvMovies.setAdapter(moviesAdapter);

        lotiView.setVisibility(View.VISIBLE);
        if (ErosApplication.movieArrayList != null && ErosApplication.movieArrayList.size() > 0) {
            showLoadedResponse(ErosApplication.movieArrayList);
        } else {
            if (isAttached) {
                getPresenter().getMovies(pageId);
                isAttached = false;
            }
        }

        moviesAdapter.setOnItemClickListener(new MoviesAdapter.ItemClick() {
            @Override
            public void onItemClick(View imageView, Movie userEntity) {
                // MovieDetailActivity.navigate((AppCompatActivity) getActivity(), view, userEntity);
                startActivity(new Intent(getActivity(), MovieDetailActivity.class)
                        .putExtra(EXTRA_IMAGE, userEntity.getPosterPath())
                        .putExtra(EXTRA_TITLE, userEntity.getTitle())
                        .putExtra(EXTRA_MOVIE_ID, userEntity.getId())
                );
            }

            @Override
            public void onFavouriteClick(Movie userEntity) {
                if (userEntity.isFavourite()) {
                    userEntity.setFavourite(false);
                } else {
                    userEntity.setFavourite(true);
                }
                moviesAdapter.notifyDataSetChanged();
                ErosApplication.movieArrayList = moviesAdapter.getUserEntities();
            }
        });
    }

    @Override
    public void setIsLoading(boolean isLoading) {
        if (isLoading) {
            showLoadingIndicator();
        } else {
            hideLoadingIndicator();
        }
    }

    public void showLoadedResponse(ArrayList<Movie> result) {
        if (result != null) {
            moviesAdapter.setUserEntities(result);
            ErosApplication.movieArrayList = moviesAdapter.getUserEntities();
            lotiView.setVisibility(View.GONE);
        } else {
            Toast.makeText(getContext(), "No movies found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showMoviesResponse(Movies result) {
        LogUtils.logE("PAGE: " + result.getPage());
        pageId = result.getPage();
        movieArrayList.addAll(result.getMovies());
        ErosApplication.movieArrayList = movieArrayList;

        if (movieArrayList != null) {
            moviesAdapter.setUserEntities(movieArrayList);
            lotiView.setVisibility(View.GONE);
        } else {
            Toast.makeText(getContext(), "No movies found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //moviesAdapter.notifyDataSetChanged();
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    public void beginSearch(String query) {
        LogUtils.logE("MoviesFragment query: " + query);

        filteredDataList = filter(movieArrayList, query);
        if (query != null & query.length() > 0) {
            LogUtils.logE("MoviesFragment query: " + movieArrayList.size());
            LogUtils.logE("MoviesFragment query: " + filteredDataList.size());
            if (filteredDataList != null && filteredDataList.size() == 0)
                Toast.makeText(getContext(), "No movies found", Toast.LENGTH_SHORT).show();
            else
                moviesAdapter.setFilter(filteredDataList);
        } else {
            LogUtils.logE("MoviesFragment query* : " + movieArrayList.size());
            LogUtils.logE("MoviesFragment query* : " + filteredDataList.size());

            moviesAdapter.setUserEntities(movieArrayList);
        }
    }

    public MoviesAdapter getAdapter() {
        return moviesAdapter;
    }

    private ArrayList<Movie> filter(ArrayList<Movie> dataList, String newText) {
        newText = newText.toLowerCase();
        String text;
        filteredDataList = new ArrayList<>();
        for (Movie dataFromDataList : dataList) {
            text = dataFromDataList.getTitle().toLowerCase();

            if (text.contains(newText)) {
                filteredDataList.add(dataFromDataList);
            }
        }

        return filteredDataList;
    }
}
