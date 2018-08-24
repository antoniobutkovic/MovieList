package com.example.toni.movielist.di.module;

import com.example.toni.movielist.interaction.ApiInteractor;
import com.example.toni.movielist.presentation.LoginPresenter;
import com.example.toni.movielist.presentation.LoginPresenterImpl;
import com.example.toni.movielist.presentation.MovieDetailsPresenter;
import com.example.toni.movielist.presentation.MovieDetailsPresenterImpl;
import com.example.toni.movielist.presentation.MoviesPresenter;
import com.example.toni.movielist.presentation.MoviesPresenterImpl;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManager;

import dagger.Module;
import dagger.Provides;

@Module(includes = {InteractorModule.class, AuthModule.class})
public class PresenterModule {

    @Provides
    public LoginPresenter provideLoginPresenter(GoogleLoginManager googleLoginManager){
        return new LoginPresenterImpl(googleLoginManager);
    }

    @Provides
    MoviesPresenter provideMoviesPresenter(ApiInteractor apiInteractor, GoogleLoginManager googleLoginManager){
        return new MoviesPresenterImpl(apiInteractor, googleLoginManager);
    }

    @Provides
    MovieDetailsPresenter provideMovieDetailsPresenter(ApiInteractor apiInteractor){
        return new MovieDetailsPresenterImpl(apiInteractor);
    }

}
