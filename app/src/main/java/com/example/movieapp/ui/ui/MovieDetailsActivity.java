package com.example.movieapp.ui.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapp.R;
import com.example.movieapp.ui.rest.ApiClientOld;
import com.example.movieapp.ui.callbacks.CallbackResponse;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.utils.Constants;
import com.example.movieapp.ui.utils.URLs;
import com.example.movieapp.ui.viewmodel.MovieDetailsViewModel;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.snackbar.Snackbar;

public class MovieDetailsActivity extends AppCompatActivity{

    private ImageView movieBackdrop;
    private TextView movieTitle;
    private TextView movieOverview;
    private TextView movieReleaseDate;
    private RatingBar movieRating;
    private MaterialFavoriteButton materialFavoriteButton;
    Movies favorite;
    MovieDetailsViewModel movieDetailsViewModel;

    private String title, details, date, poster;
    private float rating;

    private ApiClientOld apiClientOld;
    private int movieId;
    private Movies movie;

    CallbackResponse callbackResponse;
    private boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);

        movieId = getIntent().getIntExtra(URLs.MOVIE_ID, movieId);
        movie = (Movies) getIntent().getSerializableExtra(Constants.MOVIE_OBJECT);
        apiClientOld = ApiClientOld.getInstance();

        initUI();
        updateUI();
        movieDetailsViewModel.checkExist(movie.getTitle());

        materialFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChecked) {
                    materialFavoriteButton.setFavorite(true, true);
                    movieDetailsViewModel.addFavorite(movie);
                    isChecked = true;
                }else {
                    materialFavoriteButton.setFavorite(false);
                    movieDetailsViewModel.deleteFavorite(movie.getId());
                    isChecked = false;
                }
            }
        });

        movieDetailsViewModel.isExist.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                materialFavoriteButton.setFavorite(aBoolean);
                if (aBoolean) {
                    isChecked = true;
                }else {
                    isChecked = false;
                }
            }
        });

        movieDetailsViewModel.responseMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(materialFavoriteButton, s, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI() {
        if (movie != null) {

            Glide.with(this)
                    .load(URLs.IMAGE_BASE_URL + movie.getBackdrop())
                    .apply(RequestOptions.placeholderOf(R.drawable.load))
                    .into(movieBackdrop);
            movieTitle.setText(movie.getTitle());
            movieOverview.setText(movie.getOverview());
            movieReleaseDate.setText(movie.getReleaseDate());
            movieRating.setRating(movie.getRating());
        }
    }

    private void initUI() {
        movieBackdrop = findViewById(R.id.movieDetailsBackdrop);
        movieTitle = findViewById(R.id.movieDetailsTitle);
        movieOverview = findViewById(R.id.movieDetailsOverview);
        movieReleaseDate = findViewById(R.id.movieDetailsReleaseDate);
        movieRating = findViewById(R.id.movieDetailsRating);
        materialFavoriteButton = findViewById(R.id.favorite_button);
    }
}
