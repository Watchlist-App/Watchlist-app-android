package com.watchlist;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.watchlist.authorization.LogedInUserContainer;
import com.watchlist.database.WatchListSQLiteOpenHelper;
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

    private Button button;
    private Button button2;
    private TextView textView;
    private TextView textView2;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        button = (Button)findViewById(R.id.showsusersbutton);
        textView = (TextView)findViewById(R.id.showsuserstextview);
        button2 = (Button)findViewById(R.id.deleteusers);
        textView2 = (TextView)findViewById(R.id.searchfield);
        searchButton = (Button)findViewById(R.id.searchbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WatchListSQLiteOpenHelper watchListSQLiteOpenHelper = new WatchListSQLiteOpenHelper(RecentActivity.this);
                LogedInUserContainer logedInUserContainer = watchListSQLiteOpenHelper.getAllUsers();
                String str = "";
                for(int i = 0; i < logedInUserContainer.getLogedInUserArrayList().size(); i++) {
                    str += logedInUserContainer.getLogedInUserArrayList().get(i).toString();
                }
                textView.setText(str);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WatchListSQLiteOpenHelper watchListSQLiteOpenHelper = new WatchListSQLiteOpenHelper(RecentActivity.this);
                String email = "a@b.ru";
                if(watchListSQLiteOpenHelper.searchUser(email) == null) {
                    textView2.setText("No such user");
                } else {
                    textView2.setText(watchListSQLiteOpenHelper.searchUser(email).toString());
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WatchListSQLiteOpenHelper watchListSQLiteOpenHelper = new WatchListSQLiteOpenHelper(RecentActivity.this);
                watchListSQLiteOpenHelper.deleteAllUsers();
            }
        });
        /*
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
        if(savedInstanceState == null) {
            displayView(0);
        }
        */
    }
}
