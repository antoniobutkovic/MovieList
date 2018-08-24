package com.example.toni.movielist.ui.login;

public interface LoginCallback {

    void onLoginSuccess(String uid);

    void onLoginFailed(String errorMsg);

}
