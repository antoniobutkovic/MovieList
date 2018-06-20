package com.example.toni.movielist.di.component;

import com.example.toni.movielist.App;
import com.example.toni.movielist.di.module.InteractorModule;
import com.example.toni.movielist.di.module.PresenterModule;
import com.example.toni.movielist.ui.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = PresenterModule.class)
public interface AppComponent {

    void inject(App app);

    void inject(LoginActivity loginActivity);

}
