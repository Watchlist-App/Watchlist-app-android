package com.watchlistapp.movielist;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.watchlistapp.R;

public class MovieListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        ActionBar actionBar = getSupportActionBar();
        String actionBarTitle = getIntent().getStringExtra("listtitle") + " (" + getIntent().getStringExtra("moviesnumber") + " movies)";
        actionBar.setTitle(actionBarTitle);
    }
}
