package com.example.toni.movielist.view;

import com.example.toni.movielist.model.MovieResponse;

public interface MoviesView {

   void showMovies(MovieResponse movieResponse);

   void showMoviesNextPage(MovieResponse movieResponse);

   void hideRefreshingBar();

   void showLogoutSuccessMessage();

   void showLogoutFailedMessage();

   void startLoginActivity();

}
