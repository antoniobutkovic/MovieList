package com.example.toni.movielist.interaction;



import android.util.Log;

import com.example.toni.movielist.Constants;
import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.model.MovieDetailsResponse;
import com.example.toni.movielist.model.MovieResponse;
import com.example.toni.movielist.network.ApiService;
import com.example.toni.movielist.network.NetworkResponse;

import java.util.concurrent.TimeUnit;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class ApiInteractorImpl implements ApiInteractor {

    private ApiService apiService;
    private CompositeDisposable compositeDisposable;

    public ApiInteractorImpl(ApiService apiService) {
        this.apiService = apiService;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getNowPlayingMovies(int page, final NetworkResponse<MovieResponse> callback) {
        compositeDisposable.add(apiService.getNowPlayingMovies(Constants.API_KEY, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                    @Override
                    public void onSuccess(MovieResponse movieResponse) {
                        callback.onSuccess(movieResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }
                }));
    }

    @Override
    public void getTopRatedMovies(int page, final NetworkResponse<MovieResponse> callback) {
        compositeDisposable.add(apiService.getTopRatedMovies(Constants.API_KEY, page).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                    @Override
                    public void onSuccess(MovieResponse movieResponse) {
                        callback.onSuccess(movieResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }
                }));
    }

    @Override
    public void getUpcomingMovies(int page, final NetworkResponse<MovieResponse> callback) {
        compositeDisposable.add(apiService.getUpcomingMovies(Constants.API_KEY, page).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                    @Override
                    public void onSuccess(MovieResponse movieResponse) {
                        callback.onSuccess(movieResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }
                }));
    }

    @Override
    public void getMovieDetails(int movieId, final NetworkResponse<MovieDetailsResponse> callback) {
        compositeDisposable.add(apiService.getMovieDetails(movieId, Constants.API_KEY).
                subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MovieDetailsResponse>() {
                    @Override
                    public void onSuccess(MovieDetailsResponse movieDetailsResponse) {
                        callback.onSuccess(movieDetailsResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }
                }));
    }

    @Override
    public void getSearchedMovies(int page, final NetworkResponse<MovieResponse> callback, String query) {
        compositeDisposable.add(apiService.getSearchedMovies(Constants.API_KEY, query, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieResponse>() {
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
                }));
    }

    @Override
    public void getMovieById(int movieId, final NetworkResponse<Movie> callback) {
        compositeDisposable.add(apiService.getMovieById(movieId, Constants.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Movie>() {
                    @Override
                    public void onSuccess(Movie movie) {
                        callback.onSuccess(movie);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                }));
    }

    @Override
    public void getSearchedMovies(String searchQuery, NetworkResponse<MovieResponse> callback) {
        PublishSubject publishSubject = PublishSubject.create();
        publishSubject.debounce(Constants.DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap(searchValue -> apiService.getSearchedMovies(Constants.API_KEY, searchQuery, Constants.MOVIES_FIRST_PAGE)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()))
                .subscribeWith(new Observer<MovieResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
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
        publishSubject.onNext(searchQuery);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
