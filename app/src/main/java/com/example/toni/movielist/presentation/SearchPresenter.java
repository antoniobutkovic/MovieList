package com.example.toni.movielist.presentation;

import com.example.toni.movielist.view.SearchView;

public interface SearchPresenter extends BasePresenter<SearchView>{

    void getSearchedMovies(int page, String query);

}
