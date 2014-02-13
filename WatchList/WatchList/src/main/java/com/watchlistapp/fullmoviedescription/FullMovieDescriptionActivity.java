package com.watchlistapp.fullmoviedescription;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.makeramen.RoundedImageView;
import com.watchlistapp.R;
import com.watchlistapp.fullmoviedescription.actors.ActorItemsContainer;
import com.watchlistapp.fullmoviedescription.actors.ActorItemsListAdapter;
import com.watchlistapp.fullmoviedescription.actors.ActorsLoader;
import com.watchlistapp.fullmoviedescription.crew.CrewItemsContainer;
import com.watchlistapp.fullmoviedescription.crew.CrewItemsListAdapter;
import com.watchlistapp.fullmoviedescription.crew.CrewLoader;
import com.watchlistapp.fullmoviedescription.fullposter.FullPosterViewActivity;
import com.watchlistapp.ratingbar.ColoredRatingBar;
import com.watchlistapp.utils.DeveloperKeys;
import com.watchlistapp.youtube.YouTubeFailureRecoveryActivity;
import com.watchlistapp.youtube.YouTubeLoader;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;

public class FullMovieDescriptionActivity extends YouTubeFailureRecoveryActivity {

    // Views
    private TextView movieTitleTextView;
    private RoundedImageView posterImageView;
    private TextView movieOverviewTextView;
    private TextView ratingTextView;
    private TextView votesTextView;
    private ColoredRatingBar coloredRatingBar;
    private TextView releaseDateTextView;
    private TextView tagLineTextView;

    private GridView genresGridView;

    private FullMovieDescriptionLoader fullMovieDescriptionLoader;

    // Actors horizontal scroll listview
    private HListView actorsHorizontalListView;
    private ActorItemsListAdapter actorItemsListAdapter;
    private ActorItemsContainer actorItemsContainer;
    ///////////////////////////////////////////////////

    // Crew horizontal scroll listview
    private HListView crewHorizontallListView;
    private CrewItemsListAdapter crewItemsListAdapter;
    private CrewItemsContainer crewItemsContainer;
    ///////////////////////////////////////////////////

    private YouTubePlayerView youTubePlayerView;
    private String movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_movie_description);

        String movieTitle = getIntent().getStringExtra("movieTitle");
        this.movieTitle = movieTitle;
        //getSupportActionBar().setTitle(movieTitle);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_background_color)));
        actionBar.setTitle(Html.fromHtml("<b><font color=\"#424242\">" + movieTitle + "</font></b>"));

        movieTitleTextView = (TextView)findViewById(R.id.full_description_movie_title);
        posterImageView = (RoundedImageView)findViewById(R.id.full_description_movie_poster);
        movieOverviewTextView = (TextView)findViewById(R.id.full_description_movie_description);
        ratingTextView = (TextView)findViewById(R.id.full_description_movie_rating);
        votesTextView = (TextView)findViewById(R.id.full_description_movie_votes);
        coloredRatingBar = (ColoredRatingBar)findViewById(R.id.full_description_movie_rating_bar);
        releaseDateTextView = (TextView)findViewById(R.id.full_description_movie_release_date);
        tagLineTextView = (TextView)findViewById(R.id.full_description_tag_line);
        genresGridView = (GridView)findViewById(R.id.full_description_genres_grid_view);
        actorsHorizontalListView = (HListView)findViewById(R.id.full_description_movie_actors_list_view);
        crewHorizontallListView = (HListView)findViewById(R.id.full_description_movie_crew_list_view);

        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(DeveloperKeys.YOUTUBE_DEVELOPER_KEY, this);

        posterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FullMovieDescriptionActivity.this, FullPosterViewActivity.class);
                intent.putExtra("posterUrl", fullMovieDescriptionLoader.getMovieDescription().getPosterPath());
                startActivity(intent);
            }
        });

        String movieId = getIntent().getStringExtra("movieId");
        fullMovieDescriptionLoader = new FullMovieDescriptionLoader(FullMovieDescriptionActivity.this, movieId, tagLineTextView, movieTitleTextView, posterImageView, movieOverviewTextView, ratingTextView, votesTextView, coloredRatingBar, releaseDateTextView, genresGridView);
        fullMovieDescriptionLoader.execute();

        actorItemsContainer = new ActorItemsContainer();
        actorItemsListAdapter = new ActorItemsListAdapter(this, actorItemsContainer);
        actorsHorizontalListView.setAdapter(actorItemsListAdapter);
        actorsHorizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FullMovieDescriptionActivity.this, FullActorDescriptionActivity.class);
                intent.putExtra("actorId", actorItemsContainer.getActorItemArrayList().get(position).getId());
                intent.putExtra("actorName", actorItemsContainer.getActorItemArrayList().get(position).getName());
                startActivity(intent);
            }
        });

        crewItemsContainer = new CrewItemsContainer();
        crewItemsListAdapter = new CrewItemsListAdapter(this, crewItemsContainer);
        crewHorizontallListView.setAdapter(crewItemsListAdapter);

        ActorsLoader actorsLoader = new ActorsLoader(this, movieId, actorItemsListAdapter, actorItemsContainer);
        actorsLoader.execute();

        CrewLoader crewLoader = new CrewLoader(this, movieId, crewItemsListAdapter, crewItemsContainer);
        crewLoader.execute();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if(!wasRestored) {
            YouTubeLoader youTubeLoader = new YouTubeLoader(movieTitle, player);
            youTubeLoader.execute();
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView)findViewById(R.id.youtube_view);
    }
}
