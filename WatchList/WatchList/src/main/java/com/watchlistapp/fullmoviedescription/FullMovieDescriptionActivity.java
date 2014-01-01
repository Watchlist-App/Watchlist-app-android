package com.watchlistapp.fullmoviedescription;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.watchlistapp.R;
import com.watchlistapp.ratingbar.ColoredRatingBar;

public class FullMovieDescriptionActivity extends ActionBarActivity {

    // Views
    private TextView movieTitleTextView;
    private ImageView posterImageView;
    private TextView movieOverviewTextView;
    private TextView ratingTextView;
    private TextView votesTextView;
    private ColoredRatingBar coloredRatingBar;
    private TextView releaseDateTextView;
    private TextView tagLineTextView;

    private GridView genresGridView;

    private FullDescriptionLoader fullDescriptionLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_movie_description);

        movieTitleTextView = (TextView)findViewById(R.id.full_description_movie_title);
        posterImageView = (ImageView)findViewById(R.id.full_description_movie_poster);
        movieOverviewTextView = (TextView)findViewById(R.id.full_description_movie_description);
        ratingTextView = (TextView)findViewById(R.id.full_description_movie_rating);
        votesTextView = (TextView)findViewById(R.id.full_description_movie_votes);
        coloredRatingBar = (ColoredRatingBar)findViewById(R.id.full_description_movie_rating_bar);
        releaseDateTextView = (TextView)findViewById(R.id.full_description_movie_release_date);
        tagLineTextView = (TextView)findViewById(R.id.full_description_tag_line);
        genresGridView = (GridView)findViewById(R.id.full_description_genres_grid_view);

        posterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FullMovieDescriptionActivity.this, FullPosterViewActivity.class);
                intent.putExtra("posterUrl", fullDescriptionLoader.getMovieDescription().getPosterPath());
                startActivity(intent);
            }
        });

        String movieId = getIntent().getStringExtra("movieId");
        fullDescriptionLoader = new FullDescriptionLoader(FullMovieDescriptionActivity.this, movieId, tagLineTextView, movieTitleTextView, posterImageView, movieOverviewTextView, ratingTextView, votesTextView, coloredRatingBar, releaseDateTextView, genresGridView);
        fullDescriptionLoader.execute();
    }
}
