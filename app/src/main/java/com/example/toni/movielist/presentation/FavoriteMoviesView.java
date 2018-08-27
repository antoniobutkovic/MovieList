package com.example.toni.movielist.presentation;


import com.example.toni.movielist.model.Movie;


public interface FavoriteMoviesView {

    void showFavoriteMovies(Movie movie);

    void startLoginActivity();

    void showLogoutSuccessMessage();

    void showLogoutFailedMessage();

    void showNoFavoriteMoviesMessage();

    void hideNoFavoriteMoviesMessage();

    void showEmptyScreen();
}
