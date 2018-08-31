package com.example.toni.movielist.presentation;


import android.view.MenuItem;

public interface FavoriteMoviesPresenter extends BasePresenter<FavoriteMoviesView>{

    void getFavoriteMovieIds(String uid, boolean isUserLoggedIn);

    void logoutUser(boolean isUserLoggedIn);

    void handleOnSearchTextChange(String newText);

    void handleOnOptionsItemSelected(MenuItem item);

    void handleFragmentVisibilityToUser(boolean isVisibleToUser);

    void checkUserAuthState(boolean isUserLoggedIn);
}
