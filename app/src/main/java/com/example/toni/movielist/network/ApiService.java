package com.example.toni.movielist.network;

import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.model.MovieDetailsResponse;
import com.example.toni.movielist.model.MovieResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/3/movie/popular?")
    Observable<MovieResponse> getNowPlayingMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("/3/movie/top_rated?")
    Observable<MovieResponse> getTopRatedMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("/3/movie/upcoming?")
    Observable<MovieResponse> getUpcomingMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("/3/movie/{movie_id}?")
    Observable<MovieDetailsResponse> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String api_key);

    @GET("/3/search/movie?")
    Observable<MovieResponse> getSearchedMovies(@Query("api_key") String api_key, @Query("query") String query, @Query("page") int page);

    @GET("/3/movie/{movie_id}?")
    Observable<Movie> getMovieById(@Path("movie_id") int movieId, @Query("api_key") String api_key);

}
