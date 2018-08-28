package com.example.toni.movielist.presentation;


import com.example.toni.movielist.Constants;
import com.example.toni.movielist.interaction.ApiInteractor;
import com.example.toni.movielist.model.MovieResponse;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManager;
import com.example.toni.movielist.ui.main.LogoutCallback;
import com.example.toni.movielist.view.MoviesView;

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
    public void getMovies(int page, String moviesType) {
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
    public void logoutUser() {
        googleLoginManager.logoutUser(getLogoutCallback());
    }

    public Callback<MovieResponse> getMoviesCallback(final int page) {
        return new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (page > Constants.MOVIES_FIRST_PAGE){
                        view.showMoviesNextPage(response.body());
                    }else {
                        view.showMovies(response.body());
                    }
                    view.hideRefreshingBar();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

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
}
