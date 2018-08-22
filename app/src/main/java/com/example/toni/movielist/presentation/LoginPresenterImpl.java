package com.example.toni.movielist.presentation;

import android.content.Intent;

import com.example.toni.movielist.ui.login.AuthCallback;
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

    public AuthCallback getAuthCallback() {
        return new AuthCallback() {
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
