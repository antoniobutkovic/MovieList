package com.example.toni.movielist.ui.details;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.toni.movielist.App;
import com.example.toni.movielist.Constants;
import com.example.toni.movielist.R;
import com.example.toni.movielist.model.MovieDetailsResponse;
import com.example.toni.movielist.presentation.MovieDetailsPresenter;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManagerImpl;
import com.example.toni.movielist.util.NetworkUtil;
import com.example.toni.movielist.util.SharedPrefsUtil;
import com.example.toni.movielist.view.MovieDetailsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends Activity implements MovieDetailsView{


    @Inject
    MovieDetailsPresenter presenter;

    @BindView(R.id.details_toolbar)
    Toolbar toolbar;

    @BindView(R.id.details_vote_textview)
    TextView voteTv;

    @BindView(R.id.details_release_date_textview)
    TextView dateTv;

    @BindView(R.id.details_overview_textview)
    TextView overviewTv;

    @BindView(R.id.details_poster_imageview)
    ImageView posterIv;

    @BindView(R.id.details_favorite_fab)
    FloatingActionButton favoriteMovieFab;

    private List<Integer> favoriteMovieIds;
    private boolean isFabChecked;
    private int movieId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        App.getComponent().inject(this);
        presenter.setView(this);

        favoriteMovieIds = new ArrayList<>();
        movieId = getMovieIdFromIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMovieDetails();
    }

    @OnClick(R.id.details_favorite_fab)
    public void onFavoriteMovieFabClicked(){
        presenter.handleFavoriteMovieFabClicked(GoogleLoginManagerImpl.isUserLoggedIn(this), isFabChecked);
    }

    @Override
    public void showLoginErrorMessage() {
        Toast.makeText(this, getResources().getString(R.string.login_first_error_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addMovieToFavorites() {
        favoriteMovieIds.add(movieId);
    }

    @Override
    public void updateDatabase() {
        presenter.updateDatabase(favoriteMovieIds, SharedPrefsUtil.getPreferencesField(this, Constants.USER_LOGIN_TOKEN));
    }

    @Override
    public void removeMovieFromFavorites() {
        favoriteMovieIds.remove((Object)movieId);
    }

    public void getMovieDetails() {
        checkIfMovieIsFaved();
        presenter.getMovieDetails(movieId, NetworkUtil.isNetworkConnected(this));
    }

    private void checkIfMovieIsFaved() {
        presenter.getFavoriteMovieIds(GoogleLoginManagerImpl.isUserLoggedIn(this), SharedPrefsUtil.getPreferencesField(this, Constants.USER_LOGIN_TOKEN));
    }

    @Override
    public void showMovieDetails(MovieDetailsResponse details) {
        setUpDetails(details);
    }

    @Override
    public void setCurrentFavoriteMovies(List<Integer> movieIds) {
        favoriteMovieIds = movieIds;
    }

    @Override
    public void checkMovieFaveStatus() {
        presenter.checkMovieFaveStatus(favoriteMovieIds, movieId);
    }

    @Override
    public void changeFabToFavoriteState() {
        favoriteMovieFab.setImageResource(R.drawable.ic_favorite_white_24dp);
        changeFavoriteFabState(true);
    }

    @Override
    public void changeFabToUnFavoriteState() {
        favoriteMovieFab.setImageResource(R.drawable.ic_un_favorite_border_white_24dp);
        changeFavoriteFabState(false);
    }

    @Override
    public void showNetworkErrorMessage() {
        Toast.makeText(this, R.string.no_internet_connection_text, Toast.LENGTH_SHORT).show();
    }

    private void changeFavoriteFabState(boolean isChecked) {
        isFabChecked = isChecked;
    }

    private void setUpDetails(MovieDetailsResponse details) {
        String posterUrl = Constants.IMAGES_BASE_URL + details.getPosterPath();
        Glide.with(getApplicationContext()).load(posterUrl).into(posterIv);
        voteTv.setText(String.valueOf(details.getVote()));
        dateTv.setText(String.valueOf(details.getReleaseDate()));
        overviewTv.setText(String.valueOf(details.getOverview()));
        toolbar.setTitle(details.getTitle());
    }

    public int getMovieIdFromIntent() {
        if (getIntent().hasExtra(Constants.MOVIE_ID)){
            return getIntent().getIntExtra(Constants.MOVIE_ID, -1);
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetailsActivityDestroyed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }
}
