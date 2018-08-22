package com.example.toni.movielist.presentation;


import com.example.toni.movielist.interaction.ApiInteractor;
import com.example.toni.movielist.model.MovieResponse;
import com.example.toni.movielist.view.MoviesView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesPresenterImpl implements MoviesPresenter{

    private MoviesView view;
    private ApiInteractor apiInteractor;

    public MoviesPresenterImpl(ApiInteractor apiInteractor){
        this.apiInteractor = apiInteractor;
    }

    @Override
    public void setView(MoviesView view) {
        this.view = view;
    }

    @Override
    public void getUpcomingMovies(int page) {
        apiInteractor.getUpcomingMovies(page, getUpcomingMoviesCallback());
    }

    @Override
    public void getNowPlayingMovies(int page) {
        apiInteractor.getNowPlayingMovies(page, getNowPlayingMoviesCallback());
    }

    @Override
    public void getTopRatedMovies(int page) {
        apiInteractor.getTopRatedMovies(page, getTopRatedMoviesCallback());
    }

    public Callback<MovieResponse> getUpcomingMoviesCallback() {
        return new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.showMovies(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        };
    }

    public Callback<MovieResponse> getNowPlayingMoviesCallback() {
        return new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.showMovies(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        };
    }

    public Callback<MovieResponse> getTopRatedMoviesCallback() {
        return new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.showMovies(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        };
    }
}
