package com.example.toni.movielist.presentation;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import com.example.toni.movielist.Constants;
import com.example.toni.movielist.R;
import com.example.toni.movielist.interaction.ApiInteractor;
import com.example.toni.movielist.model.MovieResponse;
import com.example.toni.movielist.network.NetworkResponse;
import com.example.toni.movielist.view.SearchView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenterImpl implements SearchPresenter{

    private ApiInteractor apiInteractor;
    private SearchView view;

    public SearchPresenterImpl(ApiInteractor apiInteractor){
        this.apiInteractor = apiInteractor;
    }

    @Override
    public void setView(SearchView searchView) {
        this.view = searchView;
    }

    @Override
    public void getSearchedMovies(int page, String query, boolean isNetworkConnected) {
        if (isNetworkConnected){
            apiInteractor.getSearchedMovies(page, getSearchedMoviesCallback(page), query);
        }else{
            view.showNetworkErrorMessage();
        }
    }

    @Override
    public void handleDataFromIntent(Intent intent) {
        if (intent.hasExtra(Constants.SEARCH_QUERY)){
            String searchQuery = intent.getStringExtra(Constants.SEARCH_QUERY);
            view.setSearchQuery(searchQuery);
        }
    }

    @Override
    public void handleOnOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_back_menu:
                view.onMenuBackItemClicked();
                break;
        }
    }

    @Override
    public void unsubscribe() {
        apiInteractor.unsubscribe();
    }

    public NetworkResponse<MovieResponse> getSearchedMoviesCallback(final int page) {
        return new NetworkResponse<MovieResponse>() {
            @Override
            public void onSuccess(MovieResponse response) {
                if (page > Constants.MOVIES_FIRST_PAGE){
                    view.showMoviesNextPage(response);
                }else {
                    view.showMovies(response);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
    }
}
