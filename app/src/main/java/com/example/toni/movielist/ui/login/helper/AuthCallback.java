package com.example.toni.movielist.ui.login.helper;

public interface AuthCallback {

    void onLoginSuccess(String uid);

    void onLoginFailed(String errorMsg);

}
