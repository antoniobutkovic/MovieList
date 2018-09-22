package com.example.toni.movielist.view;

import com.example.toni.movielist.model.MovieResponse;

public interface MoviesView {

   void showMovies(MovieResponse movieResponse);

   void showMoviesNextPage(MovieResponse movieResponse);

   void hideRefreshingBar();

   void showLogoutSuccessMessage();

   void showLogoutFailedMessage();

   void startLoginActivity();

   void onLogoutMenuItemClicked();

   void showLoginRequiredMessage();

   void showNetworkErrorMessage();

   void updateUiWithSearchedResults(MovieResponse movieResponse);

   void setupUiAfterSearchIsFinished();
}
