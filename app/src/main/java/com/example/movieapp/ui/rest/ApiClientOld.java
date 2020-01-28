package com.example.movieapp.ui.rest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.movieapp.ui.App;
import com.example.movieapp.ui.callbacks.MovieApi;
import com.example.movieapp.ui.utils.URLs;

import okhttp3.Cache;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientOld {

    private static ApiClientOld apiClientOld;

    private MovieApi movieApi;
    private static int cacheSize = 10 * 1024 * 1024;
    private static Cache cache = new Cache(App.context.getCacheDir(), cacheSize);

    public ApiClientOld(MovieApi movieApi) {
        this.movieApi = movieApi;
    }

    public static ApiClientOld getInstance() {
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .cache(cache)
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Interceptor.Chain chain)
//                            throws IOException {
//                        Request request = chain.request();
//                        if (!isNetworkAvailable()) {
//                            int maxStale = 60 * 60 * 24 * 28;
//                            request = request
//                                    .newBuilder()
//                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                                    .build();
//                        }
//                        return chain.proceed(request);
//                    }
//                })
//                .build();

        if (apiClientOld == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URLs.MOVIES_URL)
                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
                    .build();

            apiClientOld = new ApiClientOld(retrofit.create(MovieApi.class));
        }

        return apiClientOld;
    }

    private static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) App.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

//    public void getPopularMovies(int page, final OnGetMoviesCallback callback) {
//
//        movieApi.getPopularMovies(URLs.API_KEY, URLs.LANGUAGE, page)
//                .enqueue(new Callback<MovieResponse>() {
//
//                    @Override
//                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
//                        if (response.isSuccessful()) {
//
//                            MovieResponse moviesResponse = response.body();
//
//                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
//                                callback.onSuccess(moviesResponse.getPage(),moviesResponse.getMovies());
////                                callbackResponse.onResponse(moviesResponse.getMovies());
//                            } else {
//                                callback.onError();
//                            }
//
//                        } else {
//                            callback.onError();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<MovieResponse> call, Throwable t) {
//                        callback.onError();
//                    }
//                });
//
//    }

}
