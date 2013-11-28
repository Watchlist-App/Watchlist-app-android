package com.watchlist;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;

import com.watchlist.navigationdrawer.NavigationDrawerAdapter;
import com.watchlist.navigationdrawer.PlaylistItem;

import java.util.ArrayList;

public class RecentActivity extends ActionBarActivity {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    // nav drawer title
    private CharSequence drawerTitle;

    // used to store app title
    private CharSequence appTitle;

    private ArrayList<PlaylistItem> playlistItemArrayList;
    private NavigationDrawerAdapter navigationDrawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        // Sets the action bar color as a drawable
        ActionBar actionBar = getSupportActionBar();
        String actionBarColor = getString(R.color.actionbar_color);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(actionBarColor)));

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        listView = (ListView)findViewById(R.id.slider_menu);

        playlistItemArrayList = new ArrayList<PlaylistItem>();
        playlistItemArrayList.add(new PlaylistItem("Drama", 20, R.drawable.playlist));

        // Setting the nav drawer adapter
        navigationDrawerAdapter = new NavigationDrawerAdapter(getApplicationContext(), playlistItemArrayList);
        listView.setAdapter(navigationDrawerAdapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer,
                R.string.app_name,
                R.string.app_name) {

            public void onDrawerClosed(View view) {
                getActionBar().setTitle(appTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        /*
        if(savedInstanceState == null) {
            displayView(0);
        }
        */
    }
}
