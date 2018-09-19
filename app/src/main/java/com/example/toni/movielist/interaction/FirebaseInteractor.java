package com.example.toni.movielist.interaction;

import com.example.toni.movielist.network.NetworkResponse;

import java.util.List;

public interface FirebaseInteractor {

    void getFavoriteMovieIds(NetworkResponse<List<Integer>> callback, String userId);

    void setFavoriteMovieIds(List<Integer> movieIds, String userId);

    void onDestroy();

}
