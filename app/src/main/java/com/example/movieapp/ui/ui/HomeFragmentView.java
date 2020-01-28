package com.example.movieapp.ui.ui;

import com.example.movieapp.ui.model.Movies;

import java.util.ArrayList;

public interface HomeFragmentView {
    void onPopularMoviesSuccess(ArrayList<Movies> movies);

    void onPopularMoviesFailed(String message);

}
