package com.example.movieapp.ui.helper;

public interface DataFetchingListener<T> {
     void onDataFetched(T response);
     void onFailed(int status);
}
