package com.example.toni.movielist.network;

import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.model.MovieDetailsResponse;
import com.example.toni.movielist.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/3/movie/popular?")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("/3/movie/top_rated?")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("/3/movie/upcoming?")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("/3/movie/{movie_id}?")
    Call<MovieDetailsResponse> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String api_key);

    @GET("/3/search/movie?")
    Call<MovieResponse> getSearchedMovies(@Query("api_key") String api_key, @Query("query") String query, @Query("page") int page);

    @GET("/3/movie/{movie_id}?")
    Call<Movie> getMovieById(@Path("movie_id") int movieId, @Query("api_key") String api_key);

}
