package com.example.toni.movielist.di.module;

import com.example.toni.movielist.presentation.LoginPresenter;
import com.example.toni.movielist.presentation.LoginPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module(includes = {InteractorModule.class, AuthModule.class})
public class PresenterModule {

    @Provides
    public LoginPresenter provideLoginPresenter(){
        return new LoginPresenterImpl();
    }

}
