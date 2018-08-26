package com.example.toni.movielist.interaction;

import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.model.MovieDetailsResponse;
import com.example.toni.movielist.model.MovieResponse;

import retrofit2.Callback;

public interface ApiInteractor {

    void getNowPlayingMovies(int page, Callback<MovieResponse> callback);

    void getTopRatedMovies(int page, Callback<MovieResponse> callback);

    void getUpcomingMovies(int page, Callback<MovieResponse> callback);

    void getMovieDetails(int movieId, Callback<MovieDetailsResponse> callback);

    void getSearchedMovies(int page, Callback<MovieResponse> callback, String query);

    void getMovieById(int movieId, Callback<Movie> callback);

}
