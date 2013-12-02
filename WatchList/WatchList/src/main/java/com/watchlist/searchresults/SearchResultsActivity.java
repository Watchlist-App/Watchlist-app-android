package com.watchlist.searchresults;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.watchlist.R;
import com.watchlist.themoviedb.SearchMovies;

/**
 * Created by VEINHORN on 02/12/13.
 */
public class SearchResultsActivity extends Activity {

    private TextView testTextView;
    private String queryString;
    private ListView searchResultsListView;
    private SearchResultsItemAdapter searchResultsItemAdapter;
    private SearchResultsContainer searchResultsContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_activity);
        queryString = getIntent().getStringExtra("query");
        //testTextView.setText(queryString);

        searchResultsListView = (ListView)findViewById(R.id.search_results_listview);
        SearchResultsContainer searchResultsContainer = new SearchResultsContainer();
        // Get the query that we sent from RecentActivity
        searchResultsItemAdapter = new SearchResultsItemAdapter(SearchResultsActivity.this, searchResultsContainer);
        searchResultsListView.setAdapter(searchResultsItemAdapter);

        SearchMovies searchMovies = new SearchMovies(SearchResultsActivity.this, queryString, searchResultsItemAdapter, searchResultsContainer);
        searchMovies.execute();
    }
}
