package com.example.movieapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapp.R;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.ui.MovieDetailsActivity;
import com.example.movieapp.ui.utils.Constants;
import com.example.movieapp.ui.utils.URLs;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieHolder> {
    ArrayList<Movies> movieList;
    Context context;

    public FavoriteMovieAdapter(ArrayList<Movies> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_upcoming_favourite, parent, false);
        return new FavoriteMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieHolder holder, final int position) {
        holder.releaseDate.setText(movieList.get(position).getReleaseDate().split("-")[0]);
        holder.title.setText(movieList.get(position).getTitle());
        holder.rating.setText(String.valueOf(movieList.get(position).getRating()));
        holder.genres.setText("");
        Glide.with(context)
                .load(URLs.IMAGE_BASE_URL + movieList.get(position).getPosterPath())
                .apply(RequestOptions.placeholderOf(R.drawable.load))
                .into(holder.poster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MovieDetailsActivity.class).putExtra(Constants.MOVIE_OBJECT, movieList.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class FavoriteMovieHolder extends RecyclerView.ViewHolder {
        TextView releaseDate;
        TextView title;
        TextView rating;
        TextView genres;
        ImageView poster;

        public FavoriteMovieHolder(@NonNull View itemView) {
            super(itemView);

            releaseDate = itemView.findViewById(R.id.tv_release_date);
            title = itemView.findViewById(R.id.tv_movie_title);
            rating = itemView.findViewById(R.id.tv_movie_genre);
            genres = itemView.findViewById(R.id.tv_movie_rating);
            poster = itemView.findViewById(R.id.iv_poster);
        }
    }
}
