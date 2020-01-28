package com.example.movieapp.ui.ui;

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
import com.example.movieapp.ui.utils.Constants;
import com.example.movieapp.ui.utils.URLs;
import com.example.movieapp.ui.viewmodel.UpcomingViewModel;

import java.util.ArrayList;

public class UpcomingFragment extends Fragment {

    private UpcomingViewModel upcomingViewModel;

    private RecyclerView upcomingRecyclerview;
    LinearLayoutManager upcomingLayout;
    private ArrayList<Movies> upComingMovieList;

    private UpcomingFavAdapter upcomingFavAdapter;
    private ApiClientOld apiClientOld;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        upcomingRecyclerview = (RecyclerView) view.findViewById(R.id.upcoming_recycler_view);

        upcomingViewModel = ViewModelProviders.of(this).get(UpcomingViewModel.class);
        upComingMovieList = new ArrayList<>();
        upcomingFavAdapter = new UpcomingFavAdapter(upComingMovieList, getContext());
        upcomingLayout = new LinearLayoutManager(this.getActivity());

        upcomingRecyclerview.setLayoutManager(upcomingLayout);
        upcomingRecyclerview.setItemAnimator(new DefaultItemAnimator());
        upcomingRecyclerview.setAdapter(upcomingFavAdapter);

//        upcomingRecyclerview.setItemAnimator(new DefaultItemAnimator());
//        upcomingRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int totalItemCount = upcomingLayout.getItemCount();
//                int visibleItemCount = upcomingLayout.getChildCount();
//                int firstVisibleItem = upcomingLayout.findFirstVisibleItemPosition();
//
//                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
//                    if (!isFetchingMovies) {
////                        getUpcomingMovies(currentPage+1);
//                    }
//                }
//            }
//        });

        upcomingViewModel.getUpComingMovies();
        upcomingViewModel.upComingMovies.observe(this, new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> movies) {
                upComingMovieList.addAll(movies);
                upcomingFavAdapter.setUpcomingMovies(upComingMovieList);
                upcomingFavAdapter.notifyDataSetChanged();

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

                if (!upcomingViewModel.isDataLoading) {
                    Log.d("DATA_FETCHING","FETCH NEXT PAGE");
                    upcomingViewModel.getUpComingMovies();
                }
            }
        }
    };

//    private void getUpcomingMovies(int page) {
//        isFetchingMovies = true;
//        apiClientOld.getUpcomingMovies(page, new OnGetMoviesCallback() {
//            @Override
//            public void onSuccess(int page, ArrayList<Movies> movies) {
//                if (upcomingFavAdapter == null) {
//                    upcomingFavAdapter = new UpcomingFavAdapter(movies,callback);
//                    upcomingRecyclerview.setAdapter(upcomingFavAdapter);
//                } else {
//                    upcomingFavAdapter.appendMovies(movies);
//                }
//                currentPage = page;
//                isFetchingMovies = false;
//            }
//
//            @Override
//            public void onError() {
//                showError();
//            }
//        });
//    }

    OnMoviesClickCallback callback = new OnMoviesClickCallback() {
        @Override
        public void onClick(Movies movie) {
            Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
//            Log.d("callback",movie==null?"null":movie.getTitle());
            intent.putExtra(URLs.MOVIE_ID, movie.getId());
            startActivity(intent);
        }
    };

    private void showError() {
        Toast.makeText(this.getActivity(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();
    }
}