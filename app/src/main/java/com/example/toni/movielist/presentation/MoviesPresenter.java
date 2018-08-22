package com.example.toni.movielist.presentation;



import com.example.toni.movielist.view.MoviesView;

public interface MoviesPresenter extends BasePresenter<MoviesView>{

    void getUpcomingMovies(int page);

    void getNowPlayingMovies(int page);

    void getTopRatedMovies(int page);

}
