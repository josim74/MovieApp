package com.example.movieapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.ui.helper.ApiHelper;
import com.example.movieapp.ui.helper.DataFetchingListener;
import com.example.movieapp.ui.model.MovieResponse;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.utils.Constants;

import java.util.ArrayList;

public class UpcomingViewModel extends ViewModel {

    public MutableLiveData<ArrayList<Movies>> upComingMovies = new MutableLiveData<>();
    public MutableLiveData<String> failedMessage = new MutableLiveData<>();
    public boolean isDataLoading = false;
    private int currentPage = 0;

    public void getUpComingMovies(){
        isDataLoading = true;
        if (currentPage == 0) {
            ApiHelper.deleteAllByCategory(Constants.UP_COMING);
        }
        ApiHelper.fetchUpComingMovies(currentPage + 1, new DataFetchingListener<MovieResponse>() {
            @Override
            public void onDataFetched(MovieResponse response) {
                isDataLoading = false;
                upComingMovies.setValue(response.getMovies());
                currentPage++;
            }

            @Override
            public void onFailed(int status) {
                isDataLoading = false;
                failedMessage.setValue("Not updated. Something went wrong!");
            }
        });
    }

    public void getUpcomingOffline() {
        ApiHelper.getMovieResponses(Constants.UP_COMING, new DataFetchingListener<ArrayList<MovieResponse>>() {
            @Override
            public void onDataFetched(ArrayList<MovieResponse> response) {
                ArrayList<Movies> movies = new ArrayList<>();
                for (MovieResponse movieResponse : response) {
                    movies.addAll(movieResponse.getMovies());
                }
                upComingMovies.setValue(movies);
            }

            @Override
            public void onFailed(int status) {

            }
        });
    }
}