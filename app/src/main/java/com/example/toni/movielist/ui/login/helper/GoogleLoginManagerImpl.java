package com.example.toni.movielist.ui.login.helper;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.toni.movielist.ui.login.LoginCallback;
import com.example.toni.movielist.ui.main.LogoutCallback;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


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
    public void onResult(Intent data, LoginCallback callback) {
        if (data != null){
            processResult(data, callback);
        }else {
            showError();
        }
    }

    @Override
    public void logoutUser(final LogoutCallback callback) {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){
                    callback.onLogoutSuccess();
                }else {
                    callback.onLogoutFailed();
                }
            }
        });
    }

    @Override
    public boolean isUserLoggedIn() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){
            return false;
        }else {
            return true;
        }
    }

    private void showError() {
    }

    private void processResult(Intent data, LoginCallback callback) {
        GoogleSignInResult loginResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (loginResult.isSuccess()){
            callback.onLoginSuccess(loginResult.getSignInAccount().getDisplayName());
        }else {
            callback.onLoginFailed(loginResult.getStatus().getStatusMessage());
        }
    }



}
