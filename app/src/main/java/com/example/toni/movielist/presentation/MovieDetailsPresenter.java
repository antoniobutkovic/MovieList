package com.example.toni.movielist.presentation;

import com.example.toni.movielist.view.MovieDetailsView;

public interface MovieDetailsPresenter extends BasePresenter<MovieDetailsView>{

    void getMovieDetails(int movieId);
}
