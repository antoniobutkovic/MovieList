package com.example.toni.movielist;

import android.app.Application;

import com.example.toni.movielist.di.component.AppComponent;
import com.example.toni.movielist.di.component.DaggerAppComponent;
import com.example.toni.movielist.di.module.AppModule;


public class App extends Application{

    private static App instance;
    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        component.inject(this);

    }

    public static AppComponent getComponent(){
        return component;
    }

    public static App getInstance() {
        return instance;
    }
}
