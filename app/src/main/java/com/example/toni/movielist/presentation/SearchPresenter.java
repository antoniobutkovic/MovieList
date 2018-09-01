package com.example.toni.movielist.presentation;

import android.content.Intent;
import android.view.MenuItem;

import com.example.toni.movielist.view.SearchView;

public interface SearchPresenter extends BasePresenter<SearchView>{

    void getSearchedMovies(int page, String query, boolean isNetworkConnected);

    void handleDataFromIntent(Intent intent);

    void handleOnOptionsItemSelected(MenuItem item);
}
