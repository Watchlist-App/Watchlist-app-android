package com.watchlistapp.fullmoviedescription;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.watchlistapp.R;

public class FullMovieDescriptionActivity extends ActionBarActivity {

    // Views
    private TextView movieTitleTextView;
    private ImageView posterImageView;
    private TextView movieOverviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_movie_description);

        movieTitleTextView = (TextView)findViewById(R.id.full_description_movie_title);
        posterImageView = (ImageView)findViewById(R.id.full_description_movie_poster);
        movieOverviewTextView = (TextView)findViewById(R.id.full_description_movie_description);

        String movieId = getIntent().getStringExtra("movieId");

        FullDescriptionLoader fullDescriptionLoader = new FullDescriptionLoader(FullMovieDescriptionActivity.this, movieId, movieTitleTextView, posterImageView, movieOverviewTextView);
        fullDescriptionLoader.execute();
    }
}
