package com.example.toni.movielist.presentation;


import android.util.Log;
import android.view.MenuItem;

import com.example.toni.movielist.Constants;
import com.example.toni.movielist.R;
import com.example.toni.movielist.interaction.ApiInteractor;
import com.example.toni.movielist.model.MovieResponse;
import com.example.toni.movielist.network.NetworkResponse;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManager;
import com.example.toni.movielist.ui.main.LogoutCallback;
import com.example.toni.movielist.view.MoviesView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesPresenterImpl implements MoviesPresenter{

    private MoviesView view;
    private ApiInteractor apiInteractor;
    private GoogleLoginManager googleLoginManager;

    public MoviesPresenterImpl(ApiInteractor apiInteractor, GoogleLoginManager googleLoginManager){
        this.apiInteractor = apiInteractor;
        this.googleLoginManager = googleLoginManager;
    }

    @Override
    public void setView(MoviesView view) {
        this.view = view;
    }

    @Override
    public void getMovies(int page, String moviesType, boolean isNetworkConnected) {
        if (isNetworkConnected){
            getMoviesForCorrespodingFragment(page, moviesType);
        }else {
            view.showNetworkErrorMessage();
        }
    }

    private void getMoviesForCorrespodingFragment(int page, String moviesType) {
        switch (moviesType){
            case Constants.MOVIE_TYPE_UPCOMING:
                getUpcomingMovies(page);
                break;
            case Constants.MOVIE_TYPE_TOP_RATED:
                getTopRatedMovies(page);
                break;
            case Constants.MOVIE_TYPE_NOW_PLAYING:
                getNowPlayingMovies(page);
                break;
        }
    }

    @Override
    public void logoutUser(boolean isUserLoggedIn) {
        if (isUserLoggedIn){
            googleLoginManager.logoutUser(getLogoutCallback());
        }else {
            view.showLoginRequiredMessage();
        }
    }

    public void getUpcomingMovies(int page) {
        apiInteractor.getUpcomingMovies(page, getMoviesCallback(page));
    }

    public void getNowPlayingMovies(int page) {
        apiInteractor.getNowPlayingMovies(page, getMoviesCallback(page));
    }

    public void getTopRatedMovies(int page) {
        apiInteractor.getTopRatedMovies(page, getMoviesCallback(page));
    }

    @Override
    public void handleOnSearchTextChange(String searchQuery) {
        if (searchQuery.isEmpty()){
            view.setupUiAfterSearchIsFinished();
        }else {
            apiInteractor.getSearchedMovies(searchQuery, getSearchedMoviesCallback());
        }
    }

    @Override
    public void handleOnOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.options_logout_menu:
                view.onLogoutMenuItemClicked();
                break;
            case R.id.options_search_menu:
                break;
        }
    }

    @Override
    public void unsubscribe() {
        apiInteractor.unsubscribe();
    }

    public NetworkResponse<MovieResponse> getMoviesCallback(final int page) {
        return new NetworkResponse<MovieResponse>() {
            @Override
            public void onSuccess(
                    MovieResponse response) {
                    if (page > Constants.MOVIES_FIRST_PAGE){
                        view.showMoviesNextPage(response);
                    }else {
                        view.showMovies(response);
                    }
                    view.hideRefreshingBar();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
    }

    public LogoutCallback getLogoutCallback() {
        return new LogoutCallback() {
            @Override
            public void onLogoutSuccess() {
                view.showLogoutSuccessMessage();
                view.startLoginActivity();
            }

            @Override
            public void onLogoutFailed() {
                view.showLogoutFailedMessage();
            }
        };
    }

    public NetworkResponse<MovieResponse> getSearchedMoviesCallback() {
        return new NetworkResponse<MovieResponse>() {
            @Override
            public void onSuccess(MovieResponse callback) {
                view.updateUiWithSearchedResults(callback);
            }

            @Override
            public void onFailure(Throwable t) {
                view.showNetworkErrorMessage();
            }
        };
    }
}
