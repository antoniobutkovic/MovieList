package com.example.toni.movielist.interaction;

import com.example.toni.movielist.model.MovieResponse;

import retrofit2.Callback;

public interface ApiInteractor {

    public void getNowPlayingMovies(int page, Callback<MovieResponse> callback);

    public void getTopRatedMovies(int page, Callback<MovieResponse> callback);

    public void getUpcomingMovies(int page, Callback<MovieResponse> callback);

}
