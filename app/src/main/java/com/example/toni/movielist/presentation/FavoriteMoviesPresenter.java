package com.example.toni.movielist.presentation;


public interface FavoriteMoviesPresenter extends BasePresenter<FavoriteMoviesView>{

    void getFavoriteMovieIds(String uid);

    void logoutUser();
}
