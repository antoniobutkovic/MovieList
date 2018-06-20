package com.example.toni.movielist.presentation;

import com.example.toni.movielist.view.LoginView;

public interface LoginPresenter extends BasePresenter<LoginView>{

    void startGoogleLoginIntent();

    void loginUserWithGoogle();

}
