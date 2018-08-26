package com.example.toni.movielist.interaction;

import com.example.toni.movielist.Constants;
import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.model.MovieDetailsResponse;
import com.example.toni.movielist.model.MovieResponse;
import com.example.toni.movielist.network.ApiService;

import retrofit2.Callback;

public class ApiInteractorImpl implements ApiInteractor{

    ApiService apiService;

    public ApiInteractorImpl(ApiService apiService){
        this.apiService = apiService;
    }

    @Override
    public void getNowPlayingMovies(int page, Callback<MovieResponse> callback) {
        apiService.getNowPlayingMovies(Constants.API_KEY, page).enqueue(callback);
    }

    @Override
    public void getTopRatedMovies(int page, Callback<MovieResponse> callback) {
        apiService.getTopRatedMovies(Constants.API_KEY, page).enqueue(callback);
    }

    @Override
    public void getUpcomingMovies(int page, Callback<MovieResponse> callback) {
        apiService.getUpcomingMovies(Constants.API_KEY, page).enqueue(callback);
    }

    @Override
    public void getMovieDetails(int movieId, Callback<MovieDetailsResponse> callback) {
        apiService.getMovieDetails(movieId, Constants.API_KEY).enqueue(callback);
    }

    @Override
    public void getSearchedMovies(int page, Callback<MovieResponse> callback, String query) {
        apiService.getSearchedMovies(Constants.API_KEY, query, page).enqueue(callback);
    }

    @Override
    public void getMovieById(int movieId, Callback<Movie> callback) {
        apiService.getMovieById(movieId, Constants.API_KEY).enqueue(callback);
    }
}
