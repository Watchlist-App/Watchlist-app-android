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
    private
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_activity);
        testTextView = (TextView)findViewById(R.id.serchresultstesttextview);
        queryString = getIntent().getStringExtra("query");
        testTextView.setText(queryString);

        // Get the query that we sent from RecentActivity
        SearchMovies searchMovies = new SearchMovies(SearchResultsActivity.this, testTextView, queryString);
        searchMovies.execute();
        testTextView.setText(searchMovies.getJsonString());

        searchResultsListView = (ListView)findViewById(R.id.search_results_listview);

    }
}
