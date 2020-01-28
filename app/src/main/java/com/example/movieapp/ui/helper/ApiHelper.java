package com.example.movieapp.ui.helper;

import com.example.movieapp.ui.model.MovieResponse;
import com.example.movieapp.ui.rest.ApiClient;
import com.example.movieapp.ui.rest.IApiClient;
import com.example.movieapp.ui.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiHelper {

    public static void fetchPopularMovies(int page_no, final DataFetchingListener<MovieResponse> listener) {
        IApiClient iApiClient = ApiClient.getClient().create(IApiClient.class);
        Call<JsonObject> call = iApiClient.getPopularMovies(URLs.API_KEY, URLs.LANGUAGE, page_no);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    if (listener != null) {
                        MovieResponse movieResponse = new Gson().fromJson(response.body(), new TypeToken<MovieResponse>() {}.getType());
                        listener.onDataFetched(movieResponse);
                    }
                } else {
                    listener.onFailed(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onFailed(0);
            }
        });
    }
    public static void fetchTopRatedMovies(int page_no, final DataFetchingListener<MovieResponse> listener) {
        IApiClient iApiClient = ApiClient.getClient().create(IApiClient.class);
        Call<JsonObject> call = iApiClient.getTopRatedMovies(URLs.API_KEY, URLs.LANGUAGE, page_no);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    if (listener != null) {
                        MovieResponse movieResponse = new Gson().fromJson(response.body(), new TypeToken<MovieResponse>() {}.getType());
                        listener.onDataFetched(movieResponse);
                    }
                } else {
                    listener.onFailed(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onFailed(0);
            }
        });
    }
    public static void fetchNowPlayingMovies(int page_no, final DataFetchingListener<MovieResponse> listener) {
        IApiClient iApiClient = ApiClient.getClient().create(IApiClient.class);
        Call<JsonObject> call = iApiClient.getNowPlayingMovies(URLs.API_KEY, URLs.LANGUAGE, page_no);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    if (listener != null) {
                        MovieResponse movieResponse = new Gson().fromJson(response.body(), new TypeToken<MovieResponse>() {}.getType());
                        listener.onDataFetched(movieResponse);
                    }
                } else {
                    listener.onFailed(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onFailed(0);
            }
        });
    }
    public static void fetchUpComingMovies(int page_no, final DataFetchingListener<MovieResponse> listener) {
        IApiClient iApiClient = ApiClient.getClient().create(IApiClient.class);
        Call<JsonObject> call = iApiClient.getUpComingMovies(URLs.API_KEY, URLs.LANGUAGE, page_no);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    if (listener != null) {
                        MovieResponse movieResponse = new Gson().fromJson(response.body(), new TypeToken<MovieResponse>() {}.getType());
                        listener.onDataFetched(movieResponse);
                    }
                } else {
                    listener.onFailed(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onFailed(0);
            }
        });
    }
}
