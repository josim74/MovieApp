package com.example.movieapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.ui.helper.ApiHelper;
import com.example.movieapp.ui.helper.DataFetchingListener;
import com.example.movieapp.ui.model.Movies;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel{

    public MutableLiveData<ArrayList<Movies>> searchedMovieList = new MutableLiveData<>();
    public MutableLiveData<String> failedMessage = new MutableLiveData<>();

    public void getSearchedItem(String searchText) {
        ApiHelper.getSearchedItems(searchText, new DataFetchingListener<ArrayList<Movies>>() {
            @Override
            public void onDataFetched(ArrayList<Movies> response) {
                searchedMovieList.setValue(response);
                if (response.size() < 1) {
                    failedMessage.setValue("No data found!");
                }
            }

            @Override
            public void onFailed(int status) {
                failedMessage.setValue("Something went wrong!");
            }
        });
    }
}
