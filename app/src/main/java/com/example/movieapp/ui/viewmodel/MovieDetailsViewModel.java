package com.example.movieapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.ui.callbacks.CallbackResponse;
import com.example.movieapp.ui.dbmanager.DBHelper;
import com.example.movieapp.ui.helper.ApiHelper;
import com.example.movieapp.ui.helper.DataFetchingListener;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.repository.ApiRepository;

import java.util.ArrayList;

public class MovieDetailsViewModel extends ViewModel implements CallbackResponse {

    CallbackResponse callbackResponse;
    ApiRepository apiRepository;
    private MutableLiveData<ArrayList<Movies>> movieMutableLiveData;
    public MutableLiveData<String> responseMessage = new MutableLiveData<>();
    public MutableLiveData<Boolean> isExist = new MutableLiveData<>();

    public void getMoviesFrmVM(CallbackResponse callbackResponse){
        this.callbackResponse=callbackResponse;
//        apiRepository = new ApiRepository(this);
        movieMutableLiveData = new MutableLiveData<>();
    }

    public void addFavorite(Movies movies){
//      apiRepository.addFavorite(movies);
        ApiHelper.insertFavorite(movies, new DataFetchingListener<Integer>() {
            @Override
            public void onDataFetched(Integer response) {
                responseMessage.setValue("Added to favorite");
            }

            @Override
            public void onFailed(int status) {
                responseMessage.setValue("Something went wrong!");
            }
        });
    }

    public void deleteFavorite(int id){
//      apiRepository.deleteFavorite(id);
        ApiHelper.deleteFavMovies(id, new DataFetchingListener<Integer>() {
            @Override
            public void onDataFetched(Integer response) {
                responseMessage.setValue("Removed from favorite");
            }

            @Override
            public void onFailed(int status) {
                responseMessage.setValue("Something went wrong!");
            }
        });
    }

    public void checkExist(String search){
//      return apiRepository.checkExist(search);
        isExist.setValue(ApiHelper.isExist(search));
    }

    @Override
    public void onResponse(Object o) {

    }
}
