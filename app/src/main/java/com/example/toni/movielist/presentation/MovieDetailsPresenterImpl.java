package com.example.toni.movielist.presentation;

import com.example.toni.movielist.interaction.ApiInteractor;
import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.model.MovieDetailsResponse;
import com.example.toni.movielist.view.MovieDetailsView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsPresenterImpl implements MovieDetailsPresenter{

    private ApiInteractor apiInteractor;
    private MovieDetailsView view;

    public MovieDetailsPresenterImpl(ApiInteractor apiInteractor){
        this.apiInteractor = apiInteractor;
    }

    @Override
    public void setView(MovieDetailsView movieDetailsView) {
        this.view = movieDetailsView;
    }

    @Override
    public void getMovieDetails(int movieId) {
        apiInteractor.getMovieDetails(movieId, getMovieDetailsCallback());
    }


    public Callback<MovieDetailsResponse> getMovieDetailsCallback() {
        return new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.showMovieDetails(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {

            }
        };
    }
}
