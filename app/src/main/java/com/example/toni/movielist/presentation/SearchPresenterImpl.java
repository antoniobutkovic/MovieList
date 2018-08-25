package com.example.toni.movielist.presentation;

import android.util.Log;

import com.example.toni.movielist.Constants;
import com.example.toni.movielist.interaction.ApiInteractor;
import com.example.toni.movielist.model.MovieResponse;
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
    public void getSearchedMovies(int page, String query) {
        apiInteractor.getSearchedMovies(page, getSearchedMoviesCallback(page), query);
    }

    public Callback<MovieResponse> getSearchedMoviesCallback(final int page) {
        return new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (page > Constants.MOVIES_FIRST_PAGE){
                    view.showMoviesNextPage(response.body());
                }else {
                    view.showMovies(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        };
    }
}
