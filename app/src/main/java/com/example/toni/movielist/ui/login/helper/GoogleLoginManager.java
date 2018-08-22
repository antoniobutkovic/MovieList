package com.example.toni.movielist.ui.login.helper;

import android.content.Intent;

import com.example.toni.movielist.ui.login.AuthCallback;


public interface GoogleLoginManager {

    Intent getLoginIntent();

    void onResult(Intent data, AuthCallback callback);

}
