package com.example.toni.movielist.ui.login.helper;

import android.content.Intent;

import com.example.toni.movielist.ui.login.AuthCallback;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;


public class GoogleLoginManagerImpl implements GoogleLoginManager {

    private GoogleApiClient googleApiClient;

    public GoogleLoginManagerImpl(GoogleApiClient googleApiClient){
        this.googleApiClient = googleApiClient;
    }

    @Override
    public Intent getLoginIntent() {
        return Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    }

    @Override
    public void onResult(Intent data) {
        if (data != null){
            processResult(data);
        }else {
            showError();
        }
    }

    private void showError() {
    }

    private void processResult(Intent data) {
        GoogleSignInResult loginResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (loginResult.isSuccess()){

        }else {
            showError();
        }
    }
}
