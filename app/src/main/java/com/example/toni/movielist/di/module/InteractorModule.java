package com.example.toni.movielist.di.module;

import com.example.toni.movielist.interaction.ApiInteractor;
import com.example.toni.movielist.interaction.ApiInteractorImpl;
import com.example.toni.movielist.network.ApiService;

import dagger.Module;
import dagger.Provides;

@Module(includes = NetworkModule.class)
public class InteractorModule {

    @Provides
    public ApiInteractor provideApiInteractor(ApiService apiService){
        return new ApiInteractorImpl(apiService);
    }

}
