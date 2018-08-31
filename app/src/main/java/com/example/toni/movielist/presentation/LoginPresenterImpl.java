package com.example.toni.movielist.presentation;

import android.content.Intent;
import android.util.Log;

import com.example.toni.movielist.Constants;
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


    public void loginUserWithGoogle(Intent data) {
        googleLoginManager.onResult(data, getAuthCallback());
    }

    @Override
    public void checkUserAuthState(boolean isUserLoggedIn) {
        if (isUserLoggedIn){
            view.startMovieListActivity();
        }
    }

    @Override
    public void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.GOOGLE_SIGN_IN_RC) {
            loginUserWithGoogle(data);
        }
    }

    @Override
    public void handleSignInButtonClicked(boolean isNetworkConnected) {
        if (isNetworkConnected){
            view.signInWithGoogle();
        }else {
            view.showNetworkErrorMessage();
        }
    }

    public LoginCallback getAuthCallback() {
        return new LoginCallback() {
            @Override
            public void onLoginSuccess(String uid) {
                view.storeUserLoginToken(uid);
                view.startMovieListActivity();
            }

            @Override
            public void onLoginFailed(String errorMsg) {
                view.showUserLogInErrorMessage(errorMsg);
            }
        };
    }
}
