package com.example.movieapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.movieapp.ui.helper.ApiHelper;
import com.example.movieapp.ui.helper.DataFetchingListener;
import com.example.movieapp.ui.model.Movies;
import java.util.ArrayList;

public class FavouriteViewModel extends ViewModel{

public MutableLiveData<ArrayList<Movies>> favMovieList = new MutableLiveData<>();
    public MutableLiveData<String> failedMessage = new MutableLiveData<>();

    public void getFavMovies() {
        ApiHelper.getFavorite(new DataFetchingListener<ArrayList<Movies>>() {
            @Override
            public void onDataFetched(ArrayList<Movies> response) {
                favMovieList.setValue(response);
            }

            @Override
            public void onFailed(int status) {
                failedMessage.setValue("No data found!");
            }
        });
    }
}