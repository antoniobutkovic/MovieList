package com.example.toni.movielist.presentation;


import com.example.toni.movielist.model.Movie;

import java.util.List;

public interface FavoriteMoviesView {

    void setMovieIds(List<Integer> movieIds);

    void getFavoriteMovies();

    void showFavoriteMovies(Movie movie);

    void startLoginActivity();
}
