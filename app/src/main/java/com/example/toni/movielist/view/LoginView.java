package com.example.toni.movielist.view;


public interface LoginView{

    void showUserLogInErrorMessage(String errorMsg);

    void startMovieListActivity();

    void storeUserLoginToken(String uid);

    void signInWithGoogle();

    void showNetworkErrorMessage();
}
