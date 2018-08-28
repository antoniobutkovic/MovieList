package com.example.toni.movielist.presentation;



import com.example.toni.movielist.view.MoviesView;

public interface MoviesPresenter extends BasePresenter<MoviesView>{

    void getMovies(int page, String moviesType);

    void logoutUser();

}
