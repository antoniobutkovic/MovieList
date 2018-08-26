package com.example.toni.movielist.presentation;

import java.util.List;

public interface FavoriteMoviesPresenter extends BasePresenter<FavoriteMoviesView>{

    void getFavoriteMovieIds(String uid);

    void getFavoriteMovies(List<Integer> movieIds);
}
