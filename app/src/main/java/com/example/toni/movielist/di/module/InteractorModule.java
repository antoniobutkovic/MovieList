package com.example.toni.movielist.di.module;

import com.example.toni.movielist.interaction.LoginInteractor;
import com.example.toni.movielist.interaction.LoginInteractorImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorModule {

    @Provides
    public LoginInteractor provideLoginInteractor(){
        return new LoginInteractorImpl();
    }

}
