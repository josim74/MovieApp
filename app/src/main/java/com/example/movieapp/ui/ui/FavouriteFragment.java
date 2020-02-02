package com.example.movieapp.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.ui.adapter.FavoriteMovieAdapter;
import com.example.movieapp.ui.adapter.UpcomingFavAdapter;
import com.example.movieapp.ui.callbacks.CallbackResponse;
import com.example.movieapp.ui.callbacks.OnMoviesClickCallback;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.utils.URLs;
import com.example.movieapp.ui.viewmodel.FavouriteViewModel;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment{
    private FavouriteViewModel favouriteViewModel;
    private ArrayList<Movies> favoriteMovieList;
    private RecyclerView favRecyclerView;
    private FavoriteMovieAdapter favAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        favouriteViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);
        favRecyclerView = view.findViewById(R.id.favRecyclerview);

        favoriteMovieList = new ArrayList<>();
        favAdapter = new FavoriteMovieAdapter(favoriteMovieList, getContext());

        favRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        favRecyclerView.setAdapter(favAdapter);

        favouriteViewModel.favMovieList.observe(this, new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> arrayList) {
                favoriteMovieList.clear();
                favoriteMovieList.addAll(arrayList);
                favAdapter.notifyDataSetChanged();
            }
        });

        favouriteViewModel.failedMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        favouriteViewModel.getFavMovies();
    }
}