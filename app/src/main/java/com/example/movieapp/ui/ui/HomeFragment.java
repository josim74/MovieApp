package com.example.movieapp.ui.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.ui.adapter.NowPlayingMovieAdapter;
import com.example.movieapp.ui.adapter.PopularMovieAdapter;
import com.example.movieapp.ui.adapter.TopRatedMovieAdapter;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.utils.CommonUtils;
import com.example.movieapp.ui.utils.Constants;
import com.example.movieapp.ui.viewmodel.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;
    RecyclerView popularRecycler;
    RecyclerView topRatedRecycler;
    RecyclerView nowPlayingRecycler;
    PopularMovieAdapter popularMovieAdapter;
    TopRatedMovieAdapter topRatedMovieAdapter;
    NowPlayingMovieAdapter nowPlayingMovieAdapter;
    private ArrayList<Movies> popularMoviesList;
    private ArrayList<Movies> topRatedMoviesList;
    private ArrayList<Movies> nowPlayingMoviesList;
    private ProgressDialog progressDialog;

    LinearLayoutManager popularManager;
    LinearLayoutManager topRatedManager;
    LinearLayoutManager nowPlayingManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        showLoading();

        popularRecycler = view.findViewById(R.id.popular_recycler_view);
        topRatedRecycler = view.findViewById(R.id.top_rated_recycler_view);
        nowPlayingRecycler = view.findViewById(R.id.now_playing_recycler_view);

        popularMoviesList = new ArrayList<>();
        topRatedMoviesList = new ArrayList<>();
        nowPlayingMoviesList = new ArrayList<>();

        popularManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        topRatedManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        nowPlayingManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);

        popularMovieAdapter = new PopularMovieAdapter(popularMoviesList,getContext());
        topRatedMovieAdapter = new TopRatedMovieAdapter(topRatedMoviesList, getContext());
        nowPlayingMovieAdapter = new NowPlayingMovieAdapter(nowPlayingMoviesList, getContext());

        popularRecycler.setLayoutManager(popularManager);
        popularRecycler.setItemAnimator(new DefaultItemAnimator());
        popularRecycler.setAdapter(popularMovieAdapter);

        topRatedRecycler.setLayoutManager(topRatedManager);
        topRatedRecycler.setItemAnimator(new DefaultItemAnimator());
        topRatedRecycler.setAdapter(topRatedMovieAdapter);

        nowPlayingRecycler.setLayoutManager(nowPlayingManager);
        nowPlayingRecycler.setItemAnimator(new DefaultItemAnimator());
        nowPlayingRecycler.setAdapter(nowPlayingMovieAdapter);


        if (CommonUtils.isNetworkAvailable()) {
            homeViewModel.getPopularMovies();
        }else {
            homeViewModel.getPopularOffline();
        }
        homeViewModel.popularMovieList.observe(this, new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> movies) {
                hideLoading();
                popularMoviesList.addAll(movies);
                popularMovieAdapter.setPopularMovies(popularMoviesList);
                popularMovieAdapter.notifyDataSetChanged();
            }
        });

        if (CommonUtils.isNetworkAvailable()) {
            homeViewModel.getTopRatedMovies();
        }else {
            homeViewModel.getTopratedOffline();
        }
        homeViewModel.topRatedMovieList.observe(this, new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> movies) {
                topRatedMoviesList.addAll(movies);
                topRatedMovieAdapter.setTopRatedMovies(topRatedMoviesList);
                topRatedMovieAdapter.notifyDataSetChanged();
            }
        });

        if (CommonUtils.isNetworkAvailable()) {
            homeViewModel.getNowPlayingMovies();
        }else {
            homeViewModel.getNowplayingOffline();
        }
        homeViewModel.nowPlayingMovieList.observe(this, new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> movies) {
                nowPlayingMoviesList.addAll(movies);
                nowPlayingMovieAdapter.setNowPlayingMovies(nowPlayingMoviesList);
                nowPlayingMovieAdapter.notifyDataSetChanged();
            }
        });

        popularRecycler.addOnScrollListener(popularOnScrollListener);
        topRatedRecycler.addOnScrollListener(topratedOnScrollListener);
        nowPlayingRecycler.addOnScrollListener(nowplayingOnScrollListener);

        return view;
    }

    private RecyclerView.OnScrollListener popularOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = popularManager.getChildCount();
            int totalItemCount = popularManager.getItemCount();
            int firstVisibleItemPosition = popularManager.findFirstVisibleItemPosition();


            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && totalItemCount >= Constants.ITEMS_PAR_PAGE) {

                if (!homeViewModel.isPopularDataLoading && CommonUtils.isNetworkAvailable()) {
                    homeViewModel.getPopularMovies();
                }
            }
        }
    };
    private RecyclerView.OnScrollListener topratedOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = topRatedManager.getChildCount();
            int totalItemCount = topRatedManager.getItemCount();
            int firstVisibleItemPosition = topRatedManager.findFirstVisibleItemPosition();


            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && totalItemCount >= Constants.ITEMS_PAR_PAGE) {

                if (!homeViewModel.isTopRatedDataLoading && CommonUtils.isNetworkAvailable()) {
                    homeViewModel.getTopRatedMovies();
                }
            }
        }
    };
    private RecyclerView.OnScrollListener nowplayingOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = nowPlayingManager.getChildCount();
            int totalItemCount = nowPlayingManager.getItemCount();
            int firstVisibleItemPosition = nowPlayingManager.findFirstVisibleItemPosition();


            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && totalItemCount >= Constants.ITEMS_PAR_PAGE) {

                if (!homeViewModel.isNowPlayingDataLoading && CommonUtils.isNetworkAvailable()) {
                    homeViewModel.getNowPlayingMovies();
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