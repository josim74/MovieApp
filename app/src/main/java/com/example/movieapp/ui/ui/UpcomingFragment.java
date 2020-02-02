package com.example.movieapp.ui.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.ui.adapter.UpcomingFavAdapter;
import com.example.movieapp.ui.rest.ApiClientOld;
import com.example.movieapp.ui.callbacks.OnMoviesClickCallback;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.utils.CommonUtils;
import com.example.movieapp.ui.utils.Constants;
import com.example.movieapp.ui.utils.URLs;
import com.example.movieapp.ui.viewmodel.UpcomingViewModel;

import java.util.ArrayList;

public class UpcomingFragment extends Fragment {

    private UpcomingViewModel upcomingViewModel;
    private RecyclerView upcomingRecyclerview;
    private LinearLayoutManager upcomingLayout;
    private ArrayList<Movies> upComingMovieList;
    private ProgressDialog progressDialog;
    private UpcomingFavAdapter upcomingFavAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        upcomingRecyclerview = view.findViewById(R.id.upcoming_recycler_view);

        upcomingViewModel = ViewModelProviders.of(this).get(UpcomingViewModel.class);
        showLoading();

        upComingMovieList = new ArrayList<>();
        upcomingFavAdapter = new UpcomingFavAdapter(upComingMovieList, getContext());
        upcomingLayout = new LinearLayoutManager(this.getActivity());

        upcomingRecyclerview.setLayoutManager(upcomingLayout);
        upcomingRecyclerview.setItemAnimator(new DefaultItemAnimator());
        upcomingRecyclerview.setAdapter(upcomingFavAdapter);

        if (CommonUtils.isNetworkAvailable()) {
            upcomingViewModel.getUpComingMovies();
        }else {
            upcomingViewModel.getUpcomingOffline();
        }
        upcomingViewModel.upComingMovies.observe(this, new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> movies) {
                hideLoading();
                upComingMovieList.addAll(movies);
                upcomingFavAdapter.setUpcomingMovies(upComingMovieList);
                upcomingFavAdapter.notifyDataSetChanged();

            }
        });

        upcomingViewModel.failedMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
        upcomingRecyclerview.addOnScrollListener(onScrollListener);
        return view;
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = upcomingLayout.getChildCount();
            int totalItemCount = upcomingLayout.getItemCount();
            int firstVisibleItemPosition = upcomingLayout.findFirstVisibleItemPosition();


            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && totalItemCount >= Constants.ITEMS_PAR_PAGE) {

                if (!upcomingViewModel.isDataLoading && CommonUtils.isNetworkAvailable()) {
                    upcomingViewModel.getUpComingMovies();
                }
            }
        }
    };


    private void showLoading() {
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(getContext());
    }

    private void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}