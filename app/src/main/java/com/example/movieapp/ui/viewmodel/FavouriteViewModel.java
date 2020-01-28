package com.example.movieapp.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.ui.callbacks.CallbackResponse;
import com.example.movieapp.ui.helper.ApiHelper;
import com.example.movieapp.ui.helper.DataFetchingListener;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.repository.ApiRepository;

import java.util.ArrayList;

public class FavouriteViewModel extends ViewModel/* implements CallbackResponse*/{

    CallbackResponse callbackResponse;
    ApiRepository apiRepository;
    public MutableLiveData<ArrayList<Movies>> favMovieList = new MutableLiveData<>();
//
//    public void getFavMovies() {
//        ApiHelper.fetchFavMovies(new DataFetchingListener<>())
//    }

//    public void getMoviesFrmVM(CallbackResponse callbackResponse){
//        this.callbackResponse=callbackResponse;
//        apiRepository = new ApiRepository(this);
//        favMovieList = new MutableLiveData<>();
//    }
//
//    public LiveData<ArrayList<Movies>> getFavorite(){
//        return movieMutableLiveData;
//    }
//
//    @Override
//    public void onResponse(Object o) {
//
//    }
}