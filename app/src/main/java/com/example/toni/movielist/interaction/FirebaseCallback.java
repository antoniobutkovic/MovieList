package com.example.toni.movielist.interaction;

import java.util.List;

public interface FirebaseCallback {

    void onFavoriteMoviesReadFinished(List<Integer> movieIds);

    void onFavoriteMoviesReadFailed();
}
