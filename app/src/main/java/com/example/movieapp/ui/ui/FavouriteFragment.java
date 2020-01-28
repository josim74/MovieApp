package com.example.movieapp.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.ui.adapter.UpcomingFavAdapter;
import com.example.movieapp.ui.callbacks.CallbackResponse;
import com.example.movieapp.ui.callbacks.OnMoviesClickCallback;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.utils.URLs;
import com.example.movieapp.ui.viewmodel.FavouriteViewModel;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment/* implements CallbackResponse */{
    private FavouriteViewModel favouriteViewModel;
    private ArrayList<Movies> favoriteMovieList;
    private RecyclerView favRecyclerView;
    private UpcomingFavAdapter upcomingFavAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        favouriteViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);

//        favouriteViewModel.getMoviesFrmVM(this);

        favoriteMovieList = new ArrayList<>();

        upcomingFavAdapter = new UpcomingFavAdapter(favoriteMovieList, getContext());


        favRecyclerView = (RecyclerView) view.findViewById(R.id.favRecyclerview);
        final LinearLayoutManager favLayoutManager = new LinearLayoutManager(this.getActivity());
        favRecyclerView.setLayoutManager(favLayoutManager);
        favRecyclerView.setAdapter(upcomingFavAdapter);

        favouriteViewModel.favMovieList.observe(this, new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> arrayList) {
                favoriteMovieList.clear();
                favoriteMovieList.addAll(arrayList);


                upcomingFavAdapter.notifyDataSetChanged();
            }
        });


        return view;
    }
//
//    OnMoviesClickCallback callback = new OnMoviesClickCallback() {
//        @Override
//        public void onClick(Movies movie) {
//            Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
////            Log.d("callback",movie==null?"null":movie.getTitle());
//            intent.putExtra(URLs.MOVIE_ID, movie.getId());
//            startActivity(intent);
//        }
//    };
//
//    @Override
//    public void onResponse(Object o) {
//
//    }
}