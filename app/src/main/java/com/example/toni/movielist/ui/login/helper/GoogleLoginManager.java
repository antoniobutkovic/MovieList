package com.example.toni.movielist.ui.login.helper;

import android.content.Intent;

import com.example.toni.movielist.ui.login.LoginCallback;
import com.example.toni.movielist.ui.main.LogoutCallback;


public interface GoogleLoginManager {

    Intent getLoginIntent();

    void onResult(Intent data, LoginCallback callback);

    void logoutUser(LogoutCallback callback);

    boolean isUserLoggedIn();

}
