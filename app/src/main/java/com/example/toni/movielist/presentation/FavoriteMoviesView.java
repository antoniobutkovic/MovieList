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

    void changeLoadingState(boolean b);

    void filterResultsInAdapter(String newText);

    void onLogoutMenuItemClicked();

    void showLoginRequiredMessage();

    void onUserVisible();

    void onUserInvisible();

    void showLoginRequiredSnackbar();

    void showNetworkErrorMessage();
}
