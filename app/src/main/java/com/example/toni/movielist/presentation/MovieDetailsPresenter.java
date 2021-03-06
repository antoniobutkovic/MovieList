package com.example.toni.movielist.presentation;

import com.example.toni.movielist.view.MovieDetailsView;

import java.util.List;

public interface MovieDetailsPresenter extends BasePresenter<MovieDetailsView>{

    void getMovieDetails(int movieId, boolean isNetworkConnected);

    void getFavoriteMovieIds(boolean userLoggedIn, String preferencesField);

    void updateDatabase(List<Integer> movieIds, String userId);

    void checkMovieFaveStatus(List<Integer> favoriteMovieIds, int movieIdFromIntent);

    void onDetailsActivityDestroyed();

    void handleFavoriteMovieFabClicked(boolean isUserLoggedIn, boolean isFabChecked);

    void unsubscribe();

}
