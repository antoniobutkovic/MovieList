package com.example.toni.movielist.network;

import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.model.MovieDetailsResponse;
import com.example.toni.movielist.model.MovieResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/3/movie/popular?")
    Single<MovieResponse> getNowPlayingMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("/3/movie/top_rated?")
    Single<MovieResponse> getTopRatedMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("/3/movie/upcoming?")
    Single<MovieResponse> getUpcomingMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("/3/movie/{movie_id}?")
    Single<MovieDetailsResponse> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String api_key);

    @GET("/3/search/movie?")
    Single<MovieResponse> getSearchedMovies(@Query("api_key") String api_key, @Query("query") String query, @Query("page") int page);

    @GET("/3/movie/{movie_id}?")
    Single<Movie> getMovieById(@Path("movie_id") int movieId, @Query("api_key") String api_key);

}
