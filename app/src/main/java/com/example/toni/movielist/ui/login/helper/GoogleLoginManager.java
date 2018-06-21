package com.example.toni.movielist.ui.login.helper;

import android.content.Intent;


public interface GoogleLoginManager {

    Intent getLoginIntent();

    void onResult(Intent data, AuthCallback callback);

}
