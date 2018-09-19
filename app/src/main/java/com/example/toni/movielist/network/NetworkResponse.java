package com.example.toni.movielist.network;

public interface NetworkResponse<T> {

    void onSuccess(T callback);

    void onFailure(Throwable t);

}
