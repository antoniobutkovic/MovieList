package com.example.toni.movielist.presentation;

import android.view.MenuItem;

import com.example.toni.movielist.R;
import com.example.toni.movielist.interaction.ApiInteractor;
import com.example.toni.movielist.interaction.FirebaseInteractor;
import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.network.NetworkResponse;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManager;
import com.example.toni.movielist.ui.main.LogoutCallback;

import java.util.List;

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

    @Override
    public void unsubscribe() {
        apiInteractor.unsubscribe();
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

    public NetworkResponse<List<Integer>> getFavoriteMovieIdsCallback() {
        return new NetworkResponse<List<Integer>>() {
            @Override
            public void onSuccess(List<Integer> callback) {
                getFavoriteMovies(callback);
            }

            @Override
            public void onFailure(Throwable t) {
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

    public NetworkResponse<Movie> getFavoriteMoviesCallback() {
        return new NetworkResponse<Movie>() {
            @Override
            public void onSuccess(Movie response) {
                view.showFavoriteMovies(response);
                view.hideNoFavoriteMoviesMessage();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
    }
}
