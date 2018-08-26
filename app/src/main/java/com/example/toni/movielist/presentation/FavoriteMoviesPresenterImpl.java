package com.example.toni.movielist.presentation;

import com.example.toni.movielist.interaction.ApiInteractor;
import com.example.toni.movielist.interaction.FirebaseCallback;
import com.example.toni.movielist.interaction.FirebaseInteractor;
import com.example.toni.movielist.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteMoviesPresenterImpl implements FavoriteMoviesPresenter{

    private FavoriteMoviesView view;
    private FirebaseInteractor firebaseInteractor;
    private ApiInteractor apiInteractor;


    public FavoriteMoviesPresenterImpl(FirebaseInteractor firebaseInteractor, ApiInteractor apiInteractor){
        this.firebaseInteractor = firebaseInteractor;
        this.apiInteractor = apiInteractor;
    }

    @Override
    public void setView(FavoriteMoviesView favoriteMoviesView) {
        this.view = favoriteMoviesView;
    }

    @Override
    public void getFavoriteMovieIds(String uid) {
        firebaseInteractor.getFavoriteMovieIds(getFavoriteMovieIdsCallback(), uid);
    }

    @Override
    public void getFavoriteMovies(List<Integer> movieIds) {
        for (Integer movieId : movieIds){
            apiInteractor.getMovieById(movieId, getFavoriteMoviesCallback());
        }
    }

    public FirebaseCallback getFavoriteMovieIdsCallback() {
        return new FirebaseCallback() {
            @Override
            public void onFavoriteMoviesReadFinished(List<Integer> movieIds) {
                view.setMovieIds(movieIds);
                view.getFavoriteMovies();
            }
        };
    }

    public Callback<Movie> getFavoriteMoviesCallback() {
        return new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null){
                    view.showFavoriteMovies(response.body());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        };
    }
}
