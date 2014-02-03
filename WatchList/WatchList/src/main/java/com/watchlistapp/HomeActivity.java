package com.watchlistapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.watchlistapp.authorization.LoggedInUser;
import com.watchlistapp.authorization.LoggedInUserContainer;
import com.watchlistapp.comingsoon.ComingSoonActivity;
import com.watchlistapp.database.WatchListDatabaseHandler;
import com.watchlistapp.movielist.MovieListActivity;
import com.watchlistapp.navigationdrawer.NavigationDrawerFragment;
import com.watchlistapp.searchresults.SearchResultsActivity;
import com.watchlistapp.watchlistserver.MovieListContainer;

public class HomeActivity extends ActionBarActivity
    implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_background_color)));
        actionBar.setTitle(Html.fromHtml("<b><font color=\"#424242\">" + getString(R.string.title_activity_home) + "</font></b>"));
        //#BFC4C3         B60606
        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_activity_home);
        // Set up the drawer
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer_activity_home, (DrawerLayout)findViewById(R.id.drawer_layout_home_activity));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_popular_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.activity_popular_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setQueryHint(getString(R.string.hint_search_activity_recent));
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // Handle the query that user types in action bar search field
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(HomeActivity.this, SearchResultsActivity.class);
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

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceHolderFragment.newInstance(position + 1))
                .commit();

    }

    public void onSectionAttached(int number) {
        switch(number) {
            case 1: {
                WatchListDatabaseHandler watchListDatabaseHandler = new WatchListDatabaseHandler(HomeActivity.this);
                LoggedInUserContainer loggedInUserContainer = watchListDatabaseHandler.getAllUsers();
                LoggedInUser loggedInUser = loggedInUserContainer.searchLastLoggedInUser();
                Toast toast = Toast.makeText(HomeActivity.this, loggedInUser.getServerId(), Toast.LENGTH_SHORT);
                toast.show();
                break;
            }
            case 2: {
                //Toast toast = Toast.makeText(PopularActivity.this, "Test 2", Toast.LENGTH_SHORT);
                //toast.show();
                Intent intent = new Intent(HomeActivity.this, ComingSoonActivity.class);
                startActivity(intent);
                break;
            }
            case 3: {
                Intent intent = new Intent(HomeActivity.this, PopularActivity.class);
                startActivity(intent);
                break;
            }

            case 4: {
                WatchListDatabaseHandler watchListDatabaseHandler = new WatchListDatabaseHandler(HomeActivity.this);
                MovieListContainer movieListContainer = watchListDatabaseHandler.getAllPlaylists();

                String listTitle = "watchlist";
                String moviesNumber = Integer.toString(watchListDatabaseHandler.getMovieIdsByListTitle(listTitle).getMovieArrayList().size());

                Intent intent = new Intent(HomeActivity.this, MovieListActivity.class);
                intent.putExtra("listtitle", listTitle);
                intent.putExtra("moviesnumber", moviesNumber);

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;
            }

            case 5: {
                WatchListDatabaseHandler watchListDatabaseHandler = new WatchListDatabaseHandler(HomeActivity.this);
                MovieListContainer movieListContainer = watchListDatabaseHandler.getAllPlaylists();

                String listTitle = "favorites";
                String moviesNumber = Integer.toString(watchListDatabaseHandler.getMovieIdsByListTitle(listTitle).getMovieArrayList().size());

                Intent intent = new Intent(HomeActivity.this, MovieListActivity.class);
                intent.putExtra("listtitle", listTitle);
                intent.putExtra("moviesnumber", moviesNumber);

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;
            }

            case 6: {
                WatchListDatabaseHandler watchListDatabaseHandler = new WatchListDatabaseHandler(HomeActivity.this);
                MovieListContainer movieListContainer = watchListDatabaseHandler.getAllPlaylists();

                String listTitle = "watched";
                String moviesNumber = Integer.toString(watchListDatabaseHandler.getMovieIdsByListTitle(listTitle).getMovieArrayList().size());

                Intent intent = new Intent(HomeActivity.this, MovieListActivity.class);
                intent.putExtra("listtitle", listTitle);
                intent.putExtra("moviesnumber", moviesNumber);

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;
            }
        }

        if(number > 6) {
            int currentPosition = number - 4;
            WatchListDatabaseHandler watchListDatabaseHandler = new WatchListDatabaseHandler(HomeActivity.this);
            MovieListContainer movieListContainer = watchListDatabaseHandler.getAllPlaylists();

            String listTitle = movieListContainer.getMovieListArrayList().get(currentPosition).getTitle();
            String moviesNumber = Integer.toString(watchListDatabaseHandler.getMovieIdsByListTitle(listTitle).getMovieArrayList().size());

            Intent intent = new Intent(HomeActivity.this, MovieListActivity.class);
            intent.putExtra("listtitle", listTitle);
            intent.putExtra("moviesnumber", moviesNumber);

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
    }

    public static class PlaceHolderFragment extends Fragment {

        private final static String ARG_SECTION_NUMBER = "section_number";

        public static PlaceHolderFragment newInstance(int sectionNumber) {
            PlaceHolderFragment fragment = new PlaceHolderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceHolderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HomeActivity)activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
