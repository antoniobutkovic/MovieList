package com.example.toni.movielist.presentation;

import com.example.toni.movielist.interaction.ApiInteractor;
import com.example.toni.movielist.interaction.FirebaseCallback;
import com.example.toni.movielist.interaction.FirebaseInteractor;
import com.example.toni.movielist.model.MovieDetailsResponse;
import com.example.toni.movielist.view.MovieDetailsView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsPresenterImpl implements MovieDetailsPresenter{

    private ApiInteractor apiInteractor;
    private FirebaseInteractor firebaseInteractor;
    private MovieDetailsView view;

    public MovieDetailsPresenterImpl(ApiInteractor apiInteractor, FirebaseInteractor firebaseInteractor){
        this.firebaseInteractor = firebaseInteractor;
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

    @Override
    public void getFavoriteMovieIds(String userId) {
        firebaseInteractor.getFavoriteMovieIds(getFavoriteMovieIdsCallback(), userId);
    }

    @Override
    public void updateDatabase(List<Integer> movieIds, String userId) {
        firebaseInteractor.setFavoriteMovieIds(getFavoriteMovieIdsCallback(), movieIds, userId);
    }

    @Override
    public void checkMovieFaveStatus(List<Integer> favoriteMovieIds, int movieIdFromIntent) {
        for (Integer movieId : favoriteMovieIds){
            if (movieId == movieIdFromIntent){
                view.changeMovieFaveStatus(true);
            }
        }
    }

    @Override
    public void onDetailsActivityDestroyed() {
        firebaseInteractor.onDestroy();
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

    public FirebaseCallback getFavoriteMovieIdsCallback() {
        return new FirebaseCallback() {
            @Override
            public void onFavoriteMoviesReadFinished(List<Integer> movieIds) {
                view.setCurrentFavoriteMovies(movieIds);
                view.checkMovieFaveStatus();
            }
        };
    }
}
