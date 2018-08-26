package com.example.toni.movielist.interaction;

import java.util.List;

public interface FirebaseInteractor {

    void getFavoriteMovieIds(FirebaseCallback callback, String userId);

    void setFavoriteMovieIds(FirebaseCallback callback, List<Integer> movieIds, String userId);

    void onDestroy();

}
