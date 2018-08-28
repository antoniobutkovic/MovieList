package com.example.toni.movielist.di.component;

import com.example.toni.movielist.App;
import com.example.toni.movielist.di.module.PresenterModule;
import com.example.toni.movielist.ui.details.DetailsActivity;
import com.example.toni.movielist.ui.login.LoginActivity;
import com.example.toni.movielist.ui.main.fragments.favorite.FavoriteMoviesFragment;
import com.example.toni.movielist.ui.main.fragments.categories.MoviesFragment;
import com.example.toni.movielist.ui.search.SearchActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = PresenterModule.class)
public interface AppComponent {

    void inject(App app);

    void inject(LoginActivity loginActivity);

    void inject(MoviesFragment upcomingMoviesFragment);

    void inject(FavoriteMoviesFragment favoriteMoviesFragment);

    void inject(DetailsActivity detailsActivity);

    void inject(SearchActivity searchActivity);

}
