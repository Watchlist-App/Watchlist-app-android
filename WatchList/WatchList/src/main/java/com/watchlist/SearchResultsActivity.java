package com.watchlist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by VEINHORN on 02/12/13.
 */
public class SearchResultsActivity extends Activity {

    private TextView testTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_activity);
        testTextView = (TextView)findViewById(R.id.serchresultstesttextview);
        // Get the query that we sent from RecentActivity
        testTextView.setText(getIntent().getStringExtra("query"));
    }
}
