package com.rnjt.eros.ui.DetailScreen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rnjt.eros.R;
import com.rnjt.eros.api.model.Movie;
import com.rnjt.eros.api.model.MovieDetail.MovieDetail;
import com.rnjt.eros.base.BaseActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

import static com.rnjt.eros.utils.Constants.EXTRA_IMAGE;
import static com.rnjt.eros.utils.Constants.EXTRA_MOVIE_ID;
import static com.rnjt.eros.utils.Constants.EXTRA_TITLE;

public class MovieDetailActivity extends BaseActivity<MovieDetailPresenter> implements MovieDetailContract.View {


    private CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.releaseStatus)
    TextView releaseStatus;
    @BindView(R.id.releaseDate)
    TextView releaseDate;
    @BindView(R.id.image)
    ImageView image;

    public static void navigate(AppCompatActivity activity, View transitionImage, Movie viewModel) {
        Intent intent = new Intent(activity, MovieDetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, viewModel.getPosterPath());
        intent.putExtra(EXTRA_TITLE, viewModel.getOriginalTitle());
        intent.putExtra(EXTRA_MOVIE_ID, viewModel.getId());

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
    }

    @Override
    protected void setUpViews() {

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        supportPostponeEnterTransition();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String itemTitle = getIntent().getStringExtra(EXTRA_TITLE);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(itemTitle);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        Picasso.get()
                .load("https://image.tmdb.org/t/p/w185_and_h278_bestv2" + getIntent().getStringExtra(EXTRA_IMAGE))
                .into(image);
        applyPalette();

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(itemTitle);
        getPresenter().getMovieDetails("" + getIntent().getIntExtra(EXTRA_MOVIE_ID, 0));
    }

    @Override
    protected MovieDetailPresenter createPresenter() {
        return new MovieDetailPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_movie_detail;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }


    @Override
    public void showMovieDetailResponse(MovieDetail movie) {
        try {
            description.setText(movie.getOverview());
            title.setText(movie.getTitle());
            releaseStatus.setText(movie.getStatus());
            releaseDate.setText(movie.getReleaseDate());
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w185_and_h278_bestv2" + movie.getPosterPath())
                    .into(image);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void setIsLoading(boolean isLoading) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int resId) {

    }

    private void applyPalette() {
        supportStartPostponedEnterTransition();
    }
}