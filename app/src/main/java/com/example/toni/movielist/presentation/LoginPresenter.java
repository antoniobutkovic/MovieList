package com.example.toni.movielist.presentation;

import android.content.Intent;

import com.example.toni.movielist.view.LoginView;

public interface LoginPresenter extends BasePresenter<LoginView>{

    void checkUserAuthState(boolean isUserLoggedIn);

    void handleOnActivityResult(int requestCode, int resultCode, Intent data);

    void handleSignInButtonClicked(boolean isNetworkConnected);
}
