package com.example.toni.movielist.presentation;

import com.example.toni.movielist.interaction.LoginInteractor;
import com.example.toni.movielist.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter{

    private LoginView view;

    @Override
    public void setView(LoginView loginView) {
        this.view = loginView;
    }

    @Override
    public void startGoogleLoginIntent() {
    }

    @Override
    public void loginUserWithGoogle() {
        view.showProgressBar();
    }
}
