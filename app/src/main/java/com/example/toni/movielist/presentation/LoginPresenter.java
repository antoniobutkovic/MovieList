package com.example.toni.movielist.presentation;

import android.content.Intent;

import com.example.toni.movielist.view.LoginView;

public interface LoginPresenter extends BasePresenter<LoginView>{

    void loginUserWithGoogle(Intent data);

    void checkUserAuthState();

}
