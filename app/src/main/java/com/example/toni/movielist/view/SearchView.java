package com.example.toni.movielist.view;

import com.example.toni.movielist.model.MovieResponse;

public interface SearchView {

    void showMovies(MovieResponse movieResponse);

    void showMoviesNextPage(MovieResponse movieResponse);

    void setSearchQuery(String searchQuery);

    void onMenuBackItemClicked();

    void showNetworkErrorMessage();
}
