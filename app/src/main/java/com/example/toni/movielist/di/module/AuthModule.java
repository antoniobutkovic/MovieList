package com.example.toni.movielist.di.module;

import android.content.Context;


import com.example.toni.movielist.R;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManager;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManagerImpl;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import dagger.Module;
import dagger.Provides;

@Module(includes = AppModule.class)
public class AuthModule {

    @Provides
    public GoogleSignInOptions provideGoogleSignInOptions(Context context){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    @Provides
    public GoogleApiClient providesGoogleApiClient(Context context, GoogleSignInOptions googleSignInOptions){
        return new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    @Provides
    public GoogleLoginManager providesGoogleLoginManager(GoogleApiClient googleApiClient){
        return new GoogleLoginManagerImpl(googleApiClient);
    }

}
