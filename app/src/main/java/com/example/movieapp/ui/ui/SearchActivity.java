package com.example.movieapp.ui.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.ui.adapter.FavoriteMovieAdapter;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.utils.CommonUtils;
import com.example.movieapp.ui.utils.Constants;
import com.example.movieapp.ui.viewmodel.SearchViewModel;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private SearchViewModel searchViewModel;
    private ArrayList<Movies> searchedMovieList;
    private RecyclerView searchRecyclerView;
    private FavoriteMovieAdapter searchAdapter;
    private ProgressDialog progressDialog;
    String searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        showLoading();

        searchText  = getIntent().getStringExtra(Constants.SEARCH_TEXT);

        searchRecyclerView = findViewById(R.id.search_recycler_view);

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        if (searchText != null) {
            searchViewModel.getSearchedItem(searchText);
        }

        searchedMovieList = new ArrayList<>();
        searchAdapter = new FavoriteMovieAdapter(searchedMovieList, this);

        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchRecyclerView.setAdapter(searchAdapter);

        searchViewModel.searchedMovieList.observe(this, new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> arrayList) {
                hideLoading();
                searchedMovieList.clear();
                searchedMovieList.addAll(arrayList);
                searchAdapter.notifyDataSetChanged();
            }
        });

        searchViewModel.failedMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                Toast.makeText(SearchActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading() {
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(this);
    }

    private void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}