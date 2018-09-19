package com.example.toni.movielist.interaction;

import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.model.MovieDetailsResponse;
import com.example.toni.movielist.model.MovieResponse;
import com.example.toni.movielist.network.NetworkResponse;

import retrofit2.Callback;

public interface ApiInteractor {

    void getNowPlayingMovies(int page, NetworkResponse<MovieResponse> callback);

    void getTopRatedMovies(int page, NetworkResponse<MovieResponse> callback);

    void getUpcomingMovies(int page, NetworkResponse<MovieResponse> callback);

    void getMovieDetails(int movieId, NetworkResponse<MovieDetailsResponse> callback);

    void getSearchedMovies(int page, NetworkResponse<MovieResponse> callback, String query);

    void getMovieById(int movieId, NetworkResponse<Movie> callback);

}
