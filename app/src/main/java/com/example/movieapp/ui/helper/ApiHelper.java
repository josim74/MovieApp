package com.example.movieapp.ui.helper;

import com.example.movieapp.ui.App;
import com.example.movieapp.ui.dbmanager.DBHelper;
import com.example.movieapp.ui.model.MovieResponse;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.rest.ApiClient;
import com.example.movieapp.ui.rest.IApiClient;
import com.example.movieapp.ui.utils.Constants;
import com.example.movieapp.ui.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

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
                        insertMovieResponse(response.body(), Constants.POPULAR);
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
                        insertMovieResponse(response.body(), Constants.TOPRATED);
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
                        insertMovieResponse(response.body(), Constants.NOW_PLAYING);
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
                        insertMovieResponse(response.body(), Constants.UP_COMING);
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

    public static void insertMovieResponse(JsonObject jsonObject, String category) {
        DBHelper helper = new DBHelper(App.context);
        helper.insertAllMovies(jsonObject, category);
    }

    public static void getMovieResponses(String category, DataFetchingListener<ArrayList<MovieResponse>> listener){
        DBHelper helper = new DBHelper(App.context);
        ArrayList<MovieResponse> movieResponses = helper.getAllMoviesByCategory(category);
        if (movieResponses.size() > 0) {
            listener.onDataFetched(movieResponses);
        }else {
            listener.onFailed(0);
        }

    }

    public static void insertFavorite(Movies movie, DataFetchingListener<Integer> listener) {
        DBHelper helper = new DBHelper(App.context);
        int response_code = helper.insertFavoriteMovie(movie);
        if (response_code == 1) {
            listener.onDataFetched(response_code);
        }else {
            listener.onFailed(0);
        }
    }

    public static void getFavorite(DataFetchingListener<ArrayList<Movies>> listener){
        DBHelper helper = new DBHelper(App.context);
        ArrayList<Movies> favMovies = helper.getFavMovies();
        if (favMovies.size() > 0) {
            listener.onDataFetched(favMovies);
        }else {
            listener.onFailed(0);
        }
    }

    public static void deleteFavMovies(int movie_id, DataFetchingListener<Integer> listener) {
        DBHelper helper = new DBHelper(App.context);
        int response_code = helper.deleteFavorite(movie_id);
        if (response_code > 0) {
            listener.onDataFetched(response_code);
        }else {
            listener.onFailed(0);
        }
    }

    public static boolean isExist(String searchItem) {
        DBHelper helper = new DBHelper(App.context);
        return helper.isExist(searchItem);
    }

    public static void deleteAllByCategory(String upComing) {
        DBHelper helper = new DBHelper(App.context);
        helper.deleteAllByCategory(upComing);
    }
}
