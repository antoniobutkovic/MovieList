package com.example.toni.movielist.di.component;

import com.example.toni.movielist.App;
import com.example.toni.movielist.di.module.InteractorModule;
import com.example.toni.movielist.di.module.PresenterModule;
import com.example.toni.movielist.ui.login.LoginActivity;
import com.example.toni.movielist.ui.main.favorite.FavoriteMoviesFragment;
import com.example.toni.movielist.ui.main.nowplaying.NowPlayingMoviesFragment;
import com.example.toni.movielist.ui.main.toprated.TopRatedMoviesFragment;
import com.example.toni.movielist.ui.main.upcoming.UpcomingMoviesFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = PresenterModule.class)
public interface AppComponent {

    void inject(App app);

    void inject(LoginActivity loginActivity);

    void inject(UpcomingMoviesFragment upcomingMoviesFragment);

    void inject(NowPlayingMoviesFragment nowPlayingMoviesFragment);

    void inject(TopRatedMoviesFragment topRatedMoviesFragment);

    void inject(FavoriteMoviesFragment favoriteMoviesFragment);

}
