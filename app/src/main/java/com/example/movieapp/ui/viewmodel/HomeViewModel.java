package com.example.movieapp.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.ui.callbacks.CallbackResponse;
import com.example.movieapp.ui.helper.ApiHelper;
import com.example.movieapp.ui.helper.DataFetchingListener;
import com.example.movieapp.ui.model.MovieResponse;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.repository.ApiRepository;
import com.example.movieapp.ui.utils.Constants;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel{

    public MutableLiveData<ArrayList<Movies>> popularMovieList = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Movies>> topRatedMovieList = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Movies>> nowPlayingMovieList = new MutableLiveData<>();
    public MutableLiveData<String> failedMessage = new MutableLiveData<>();

    public boolean isPopularDataLoading = false;
    public boolean isTopRatedDataLoading = false;
    public boolean isNowPlayingDataLoading = false;

    private int popular_page_no = 0;
    private int toprated_page_no = 0;
    private int now_playing_page_no = 0;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void getPopularMovies() {
        isPopularDataLoading = true;
        if (popular_page_no == 0) {
            ApiHelper.deleteAllByCategory(Constants.POPULAR);
        }
        ApiHelper.fetchPopularMovies(popular_page_no+1, new DataFetchingListener<MovieResponse>() {
            @Override
            public void onDataFetched(MovieResponse response) {
                isPopularDataLoading = false;
                popularMovieList.setValue(response.getMovies());
                popular_page_no++;
            }

            @Override
            public void onFailed(int status) {
                isPopularDataLoading = false;
                failedMessage.setValue("No data updated. Something went wrong!");
            }
        });
    }

    public void getPopularOffline() {
        ApiHelper.getMovieResponses(Constants.POPULAR, new DataFetchingListener<ArrayList<MovieResponse>>() {
            @Override
            public void onDataFetched(ArrayList<MovieResponse> response) {
                ArrayList<Movies> movies = new ArrayList<>();
                for (MovieResponse movieResponse : response) {
                    movies.addAll(movieResponse.getMovies());
                }
                popularMovieList.setValue(movies);
            }

            @Override
            public void onFailed(int status) {

            }
        });
    }

    public void getTopRatedMovies() {
        if (toprated_page_no == 0) {
            ApiHelper.deleteAllByCategory(Constants.TOPRATED);
        }
        isTopRatedDataLoading = true;
        ApiHelper.fetchTopRatedMovies(toprated_page_no+1, new DataFetchingListener<MovieResponse>() {
            @Override
            public void onDataFetched(MovieResponse response) {
                isTopRatedDataLoading = false;
                topRatedMovieList.setValue(response.getMovies());
                toprated_page_no++;
            }

            @Override
            public void onFailed(int status) {
                isTopRatedDataLoading = false;
                failedMessage.setValue("No data updated. Something went wrong!");
            }
        });
    }

    public void getTopratedOffline() {
        ApiHelper.getMovieResponses(Constants.TOPRATED, new DataFetchingListener<ArrayList<MovieResponse>>() {
            @Override
            public void onDataFetched(ArrayList<MovieResponse> response) {
                ArrayList<Movies> movies = new ArrayList<>();
                for (MovieResponse movieResponse : response) {
                    movies.addAll(movieResponse.getMovies());
                }
                topRatedMovieList.setValue(movies);
            }

            @Override
            public void onFailed(int status) {

            }
        });
    }

    public void getNowPlayingMovies() {
        if (now_playing_page_no == 0) {
            ApiHelper.deleteAllByCategory(Constants.NOW_PLAYING);
        }
        isNowPlayingDataLoading = true;
        ApiHelper.fetchNowPlayingMovies(now_playing_page_no+1, new DataFetchingListener<MovieResponse>() {
            @Override
            public void onDataFetched(MovieResponse response) {
                isNowPlayingDataLoading = false;
                nowPlayingMovieList.setValue(response.getMovies());
                now_playing_page_no++;
            }

            @Override
            public void onFailed(int status) {
                isNowPlayingDataLoading = false;
                failedMessage.setValue("No data updated. Something went wrong!");
            }
        });
    }



    public void getNowplayingOffline() {
        ApiHelper.getMovieResponses(Constants.NOW_PLAYING, new DataFetchingListener<ArrayList<MovieResponse>>() {
            @Override
            public void onDataFetched(ArrayList<MovieResponse> response) {
                ArrayList<Movies> movies = new ArrayList<>();
                for (MovieResponse movieResponse : response) {
                    movies.addAll(movieResponse.getMovies());
                }
                nowPlayingMovieList.setValue(movies);
            }

            @Override
            public void onFailed(int status) {

            }
        });
    }
}