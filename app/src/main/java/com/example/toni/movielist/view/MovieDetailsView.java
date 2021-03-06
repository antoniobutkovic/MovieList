package com.example.toni.movielist.view;

import com.example.toni.movielist.model.MovieDetailsResponse;

import java.util.List;

public interface MovieDetailsView {

    void showMovieDetails(MovieDetailsResponse movieDetailsResponse);

    void setCurrentFavoriteMovies(List<Integer> movieIds);

    void checkMovieFaveStatus();

    void showLoginErrorMessage();

    void removeMovieFromFavorites();

    void addMovieToFavorites();

    void updateDatabase();

    void changeFabToFavoriteState();

    void changeFabToUnFavoriteState();

    void showNetworkErrorMessage();
}
