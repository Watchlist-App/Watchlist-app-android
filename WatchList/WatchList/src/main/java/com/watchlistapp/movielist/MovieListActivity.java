package com.watchlistapp.movielist;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.watchlistapp.R;
import com.watchlistapp.fullmoviedescription.FullMovieDescriptionActivity;
import com.watchlistapp.searchresults.SearchResultsContainer;
import com.watchlistapp.searchresults.SearchResultsItemAdapter;

public class MovieListActivity extends ActionBarActivity {

    private ListView searchResultsListView;
    private SearchResultsItemAdapter searchResultsItemAdapter;
    private SearchResultsContainer searchResultsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        ActionBar actionBar = getSupportActionBar();
        String listTitle = getIntent().getStringExtra("listtitle");
        String actionBarTitle = listTitle + " (" + getIntent().getStringExtra("moviesnumber") + " movies)";
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_background_color)));
        actionBar.setTitle(Html.fromHtml("<b><font color=\"#424242\">" + actionBarTitle + "</font></b>"));

        searchResultsListView = (ListView)findViewById(R.id.movie_list_listview);

        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MovieListActivity.this, FullMovieDescriptionActivity.class);
                intent.putExtra("movieId", searchResultsContainer.getSearchResultsItemArrayList().get(position).getMovieId());
                intent.putExtra("movieTitle", searchResultsContainer.getSearchResultsItemArrayList().get(position).getTitle());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
            }
        });

        searchResultsContainer = new SearchResultsContainer();
        searchResultsItemAdapter = new SearchResultsItemAdapter(MovieListActivity.this, searchResultsContainer, MovieListActivity.this, searchResultsListView);
        searchResultsListView.setAdapter(searchResultsItemAdapter);

        LoadMovie loadMovie = new LoadMovie(MovieListActivity.this, listTitle, searchResultsItemAdapter, searchResultsContainer, MovieListActivity.this);
        loadMovie.execute();
    }
}
