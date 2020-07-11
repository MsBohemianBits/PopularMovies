package com.techiasolutions.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.util.Objects;


public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent mIntent = getIntent();
        MoviesResult moviesResult = mIntent.getParcelableExtra("moveDetailData");

        TextView mTitle = (TextView) findViewById(R.id.movietitle);
        mTitle.setText(Objects.requireNonNull(moviesResult).getTitle());

        TextView synopsis = (TextView) findViewById(R.id.synopsis);
        synopsis.setText(moviesResult.getOverview());

        TextView rating = (TextView) findViewById(R.id.rating);
        rating.setText(moviesResult.getVoteAverage());

        TextView releasedate = (TextView) findViewById(R.id.releasedate);
        releasedate.setText(moviesResult.getReleaseDate());

        ImageView moviePoster = (ImageView) findViewById(R.id.movieposter);

        String photo = getResources().getString(R.string.photo_url) + moviesResult.getPosterPath();
        Picasso.get().load(photo)
                .resize(1000, 1000)
                .error(R.drawable.ic_launcher_background)
                .into(moviePoster);
    }
}