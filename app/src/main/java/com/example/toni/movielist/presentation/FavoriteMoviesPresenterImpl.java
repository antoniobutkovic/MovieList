package com.example.toni.movielist.presentation;

import android.util.Log;
import android.view.MenuItem;

import com.example.toni.movielist.R;
import com.example.toni.movielist.interaction.ApiInteractor;
import com.example.toni.movielist.interaction.FirebaseCallback;
import com.example.toni.movielist.interaction.FirebaseInteractor;
import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManager;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManagerImpl;
import com.example.toni.movielist.ui.main.LogoutCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteMoviesPresenterImpl implements FavoriteMoviesPresenter{

    private FavoriteMoviesView view;
    private FirebaseInteractor firebaseInteractor;
    private ApiInteractor apiInteractor;
    private GoogleLoginManager googleLoginManager;


    public FavoriteMoviesPresenterImpl(FirebaseInteractor firebaseInteractor, ApiInteractor apiInteractor, GoogleLoginManager googleLoginManager){
        this.firebaseInteractor = firebaseInteractor;
        this.apiInteractor = apiInteractor;
        this.googleLoginManager = googleLoginManager;
    }

    @Override
    public void setView(FavoriteMoviesView favoriteMoviesView) {
        this.view = favoriteMoviesView;
    }

    @Override
    public void getFavoriteMovies(String uid, boolean isUserLoggedIn, boolean isNetworkConnected) {
        if (isNetworkConnected){
            if (isUserLoggedIn){
                firebaseInteractor.getFavoriteMovieIds(getFavoriteMovieIdsCallback(), uid);
            }
        }else {
            view.showNetworkErrorMessage();
        }
    }

    @Override
    public void logoutUser(boolean isUserLoggedIn) {
        if (isUserLoggedIn){
            googleLoginManager.logoutUser(getLogoutCallback());
        }else {
            view.showLoginRequiredMessage();
        }
    }

    @Override
    public void handleOnSearchTextChange(String newText) {
        if (newText.isEmpty()){
            view.changeLoadingState(false);
        }else {
            view.changeLoadingState(true);
        }
        view.filterResultsInAdapter(newText);
    }

    @Override
    public void handleOnOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.options_logout_menu:
                view.onLogoutMenuItemClicked();
                break;
            case R.id.options_search_menu:
                break;
        }
    }

    @Override
    public void handleFragmentVisibilityToUser(boolean isVisibleToUser) {
        if (isVisibleToUser){
            view.onUserVisible();
        }else {
            view.onUserInvisible();
        }
    }

    @Override
    public void checkUserAuthState(boolean isUserLoggedIn) {
        if (!isUserLoggedIn){
            view.showLoginRequiredSnackbar();
        }
    }

    public LogoutCallback getLogoutCallback() {
        return new LogoutCallback() {
            @Override
            public void onLogoutSuccess() {
                view.showLogoutSuccessMessage();
                view.startLoginActivity();
            }

            @Override
            public void onLogoutFailed() {
                view.showLogoutFailedMessage();
            }
        };
    }

    public FirebaseCallback getFavoriteMovieIdsCallback() {
        return new FirebaseCallback() {
            @Override
            public void onFavoriteMoviesReadFinished(List<Integer> movieIds) {
                getFavoriteMovies(movieIds);
            }

            @Override
            public void onFavoriteMoviesReadFailed() {
                view.showNoFavoriteMoviesMessage();
                view.showEmptyScreen();
            }
        };
    }

    public void getFavoriteMovies(List<Integer> movieIds) {
        for (Integer movieId : movieIds){
            apiInteractor.getMovieById(movieId, getFavoriteMoviesCallback());
        }
    }

    public Callback<Movie> getFavoriteMoviesCallback() {
        return new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null){
                    view.showFavoriteMovies(response.body());
                    view.hideNoFavoriteMoviesMessage();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        };
    }
}
