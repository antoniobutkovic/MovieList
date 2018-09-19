package com.example.toni.movielist.presentation;

import com.example.toni.movielist.interaction.ApiInteractor;
import com.example.toni.movielist.interaction.FirebaseInteractor;
import com.example.toni.movielist.model.MovieDetailsResponse;
import com.example.toni.movielist.network.NetworkResponse;
import com.example.toni.movielist.view.MovieDetailsView;

import java.util.List;

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
    public void getMovieDetails(int movieId, boolean isNetworkConnected) {
        if (isNetworkConnected){
            apiInteractor.getMovieDetails(movieId, getMovieDetailsCallback());
        }else {
            view.showNetworkErrorMessage();
        }

    }

    @Override
    public void getFavoriteMovieIds(boolean isUserLoggedIn, String userId) {
        if (isUserLoggedIn){
            firebaseInteractor.getFavoriteMovieIds(getFavoriteMovieIdsCallback(), userId);
        }
    }

    @Override
    public void updateDatabase(List<Integer> movieIds, String userId) {
        firebaseInteractor.setFavoriteMovieIds(movieIds, userId);
    }

    @Override
    public void checkMovieFaveStatus(List<Integer> favoriteMovieIds, int movieIdFromIntent) {
        for (Integer movieId : favoriteMovieIds){
            if (movieId == movieIdFromIntent){
                changeMovieFaveStatus(true);
            }
        }
    }

    @Override
    public void onDetailsActivityDestroyed() {
        firebaseInteractor.onDestroy();
    }

    @Override
    public void handleFavoriteMovieFabClicked(boolean isUserLoggedIn, boolean isFabChecked) {
        if (isUserLoggedIn){
            if (isFabChecked){
                changeMovieFaveStatus(false);
                view.removeMovieFromFavorites();
                view.updateDatabase();
            }else {
                changeMovieFaveStatus(true);
                view.addMovieToFavorites();
                view.updateDatabase();
            }
        }else {
            view.showLoginErrorMessage();
        }
    }

    @Override
    public void unsubscribe() {
        apiInteractor.unsubscribe();
    }

    public void changeMovieFaveStatus(boolean isFavorite) {
        if (isFavorite){
            view.changeFabToFavoriteState();
        }else {
            view.changeFabToUnFavoriteState();
        }
    }


    public NetworkResponse<MovieDetailsResponse> getMovieDetailsCallback() {
        return new NetworkResponse<MovieDetailsResponse>() {
            @Override
            public void onSuccess(MovieDetailsResponse response) {
                view.showMovieDetails(response);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
    }

    public NetworkResponse<List<Integer>> getFavoriteMovieIdsCallback() {
        return new NetworkResponse<List<Integer>>() {
            @Override
            public void onSuccess(List<Integer> callback) {
                view.setCurrentFavoriteMovies(callback);
                view.checkMovieFaveStatus();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
    }
}
