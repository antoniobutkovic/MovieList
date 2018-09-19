package com.example.toni.movielist.interaction;

import com.example.toni.movielist.Constants;
import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.model.MovieDetailsResponse;
import com.example.toni.movielist.model.MovieResponse;
import com.example.toni.movielist.network.ApiService;
import com.example.toni.movielist.network.NetworkResponse;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Callback;

public class ApiInteractorImpl implements ApiInteractor{

    ApiService apiService;

    public ApiInteractorImpl(ApiService apiService){
        this.apiService = apiService;
    }

    @Override
    public void getNowPlayingMovies(int page, final NetworkResponse<MovieResponse> callback) {
        apiService.getNowPlayingMovies(Constants.API_KEY, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        callback.onSuccess(movieResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void getTopRatedMovies(int page, final NetworkResponse<MovieResponse> callback) {
        apiService.getTopRatedMovies(Constants.API_KEY, page).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        callback.onSuccess(movieResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getUpcomingMovies(int page, final NetworkResponse<MovieResponse> callback) {
        apiService.getUpcomingMovies(Constants.API_KEY, page).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        callback.onSuccess(movieResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getMovieDetails(int movieId, final NetworkResponse<MovieDetailsResponse> callback) {
        apiService.getMovieDetails(movieId, Constants.API_KEY).
                subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieDetailsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieDetailsResponse movieDetailsResponse) {
                        callback.onSuccess(movieDetailsResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getSearchedMovies(int page, final NetworkResponse<MovieResponse> callback, String query) {
        apiService.getSearchedMovies(Constants.API_KEY, query, page).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        callback.onSuccess(movieResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getMovieById(int movieId, final NetworkResponse<Movie> callback) {
        apiService.getMovieById(movieId, Constants.API_KEY).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Movie movie) {
                        callback.onSuccess(movie);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
