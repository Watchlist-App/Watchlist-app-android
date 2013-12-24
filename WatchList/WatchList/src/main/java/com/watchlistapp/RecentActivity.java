package com.watchlistapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.watchlistapp.searchresults.SearchResultsActivity;

public class RecentActivity extends ActionBarActivity {

    private Button button;
    private Button button2;
    private TextView textView;
    private TextView textView2;
    private Button searchButton;
    private Button clearButton;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        // Sets the action bar color as a drawable
        ActionBar actionBar = getSupportActionBar();
        String actionBarColor = getString(R.color.actionbar_color);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(actionBarColor)));

        button = (Button)findViewById(R.id.showsusersbutton);
        textView = (TextView)findViewById(R.id.showsuserstextview);
        button2 = (Button)findViewById(R.id.deleteusers);
        textView2 = (TextView)findViewById(R.id.searchfield);
        searchButton = (Button)findViewById(R.id.searchbutton);
        clearButton = (Button)findViewById(R.id.clearButton);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_recent_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.activity_recent_search);
        searchView = (SearchView)MenuItemCompat.getActionView(searchItem);
        //searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.hint_search_activity_recent));
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // Handle the query that user types in action bar search field
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(RecentActivity.this, SearchResultsActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
