package com.example.movieapp.ui.rest;

import com.example.movieapp.ui.utils.URLs;
import com.google.gson.JsonObject;
import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApiClient {

    @GET(URLs.POPULAR_MOVIES)
    Call<JsonObject> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
    @GET(URLs.TOP_RATED_MOVIES)
    Call<JsonObject> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
    @GET(URLs.MOVIES_NOW_PLAYING)
    Call<JsonObject> getNowPlayingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
    @GET(URLs.UPCOMING_MOVIES)
    Call<JsonObject> getUpComingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET(URLs.SEARCH)
    Call<JsonObject> search(
            @Query("api_key") String apiKey,
            @Query("query") String language
    );
}
