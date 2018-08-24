package com.example.toni.movielist.presentation;

import android.content.Intent;

import com.example.toni.movielist.ui.login.LoginCallback;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManager;
import com.example.toni.movielist.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter{

    private LoginView view;
    private GoogleLoginManager googleLoginManager;

    public LoginPresenterImpl(GoogleLoginManager googleLoginManager){
        this.googleLoginManager = googleLoginManager;
    }

    @Override
    public void setView(LoginView loginView) {
        this.view = loginView;
    }

    @Override
    public void loginUserWithGoogle(Intent data) {
        googleLoginManager.onResult(data, getAuthCallback());
    }

    @Override
    public void checkUserAuthState() {
        boolean isUserLoggedIn = googleLoginManager.isUserLoggedIn();
        if (isUserLoggedIn){
            view.startMovieListActivity();
        }
    }

    public LoginCallback getAuthCallback() {
        return new LoginCallback() {
            @Override
            public void onLoginSuccess(String uid) {
                view.startMovieListActivity();
            }

            @Override
            public void onLoginFailed(String errorMsg) {
                view.showUserLogInErrorMessage(errorMsg);
            }
        };
    }
}
