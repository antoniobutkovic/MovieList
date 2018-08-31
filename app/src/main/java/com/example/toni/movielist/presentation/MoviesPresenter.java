package com.example.toni.movielist.presentation;



import android.view.MenuItem;

import com.example.toni.movielist.view.MoviesView;

public interface MoviesPresenter extends BasePresenter<MoviesView>{

    void getMovies(int page, String moviesType);

    void logoutUser(boolean isUserLoggedIn);

    void handleOnSearchTextChange(String newText);

    void handleOnOptionsItemSelected(MenuItem item);
}
