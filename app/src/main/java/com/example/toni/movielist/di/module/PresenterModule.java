package com.example.toni.movielist.di.module;

import com.example.toni.movielist.presentation.LoginPresenter;
import com.example.toni.movielist.presentation.LoginPresenterImpl;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManager;

import dagger.Module;
import dagger.Provides;

@Module(includes = {InteractorModule.class, AuthModule.class})
public class PresenterModule {

    @Provides
    public LoginPresenter provideLoginPresenter(GoogleLoginManager googleLoginManager){
        return new LoginPresenterImpl(googleLoginManager);
    }

}
