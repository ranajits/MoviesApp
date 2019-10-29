package com.rnjt.eros.ui.MainScreen.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rnjt.eros.R;
import com.rnjt.eros.api.model.Movie;
import com.rnjt.eros.base.BaseFragment;
import com.rnjt.eros.base.BasePresenter;
import com.rnjt.eros.base.MvpPresenter;
import com.rnjt.eros.ui.DetailScreen.MovieDetailActivity;
import com.rnjt.eros.ui.ErosApplication;
import com.rnjt.eros.ui.MainScreen.fragments.adapter.MoviesAdapter;

import java.util.ArrayList;

import static com.rnjt.eros.utils.Constants.EXTRA_IMAGE;
import static com.rnjt.eros.utils.Constants.EXTRA_MOVIE_ID;
import static com.rnjt.eros.utils.Constants.EXTRA_TITLE;


public class FavouriteFragment extends BaseFragment {
    View view;
    RecyclerView rvFavouriteUser;
    MoviesAdapter favouriteAdapter;
    LinearLayout layNothing;
    private GridLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favourite, container, false);
        rvFavouriteUser = view.findViewById(R.id.rvFavouriteUser);
        layNothing = view.findViewById(R.id.layNothing);
        return view;
    }

    @Override
    protected void setUpViews() {
        layoutManager = new GridLayoutManager(getActivity(), 2);
        rvFavouriteUser.setLayoutManager(layoutManager);
        rvFavouriteUser.setHasFixedSize(true);
        favouriteAdapter = new MoviesAdapter(getActivity());
        rvFavouriteUser.setAdapter(favouriteAdapter);

        ArrayList<Movie> userInfoArrayListTemp = new ArrayList<>();
        ArrayList<Movie> userInfoArrayList = new ArrayList<>();
        userInfoArrayListTemp = ErosApplication.movieArrayList;
        if (userInfoArrayListTemp != null && userInfoArrayListTemp.size() > 0) {
            for (Movie userInfo : userInfoArrayListTemp) {
                if (userInfo.isFavourite())
                    userInfoArrayList.add(userInfo);
            }
        }

        if (userInfoArrayList != null && userInfoArrayList.size() > 0) {
            layNothing.setVisibility(View.GONE);
        } else {
            layNothing.setVisibility(View.VISIBLE);
        }

        favouriteAdapter.setUserEntities(userInfoArrayList);

        favouriteAdapter.setOnItemClickListener(new MoviesAdapter.ItemClick() {
            @Override
            public void onItemClick(View view, Movie userEntity) {
                startActivity(new Intent(getActivity(), MovieDetailActivity.class)
                        .putExtra(EXTRA_IMAGE, userEntity.getPosterPath())
                        .putExtra(EXTRA_TITLE, userEntity.getTitle())
                        .putExtra(EXTRA_MOVIE_ID, userEntity.getId()));

            }

            @Override
            public void onFavouriteClick(Movie userEntity) {
                if (userEntity.isFavourite()) {
                    favouriteAdapter.getUserEntities().remove(userEntity);

                    for (Movie userInfo : ErosApplication.movieArrayList) {
                        if (userEntity.getId() == userInfo.getId())
                            userInfo.setFavourite(false);
                    }
                } else {
                    userEntity.setFavourite(true);
                }
                favouriteAdapter.notifyDataSetChanged();

                if (favouriteAdapter.getUserEntities() != null && favouriteAdapter.getUserEntities().size() > 0) {
                    layNothing.setVisibility(View.GONE);
                } else {
                    layNothing.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    protected MvpPresenter createPresenter() {
        return new BasePresenter.AppPresenter();
    }
}
