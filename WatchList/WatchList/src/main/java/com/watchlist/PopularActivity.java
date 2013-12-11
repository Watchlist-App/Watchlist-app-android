package com.watchlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.watchlist.comingsoon.ComingSoonActivity;
import com.watchlist.navigationdrawer.NavigationDrawerFragment;

/**
 * Created by VEINHORN on 10/12/13.
 */
public class PopularActivity extends ActionBarActivity
    implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout));


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
                Intent intent = new Intent(PopularActivity.this, ComingSoonActivity.class);
                startActivity(intent);
                break;
            }
            case 2: {
                //Toast toast = Toast.makeText(PopularActivity.this, "Test 2", Toast.LENGTH_SHORT);
                //toast.show();
                break;
            }
            case 3: {
                //Toast toast = Toast.makeText(PopularActivity.this, "Test 3", Toast.LENGTH_SHORT);
                //toast.show();
                break;
            }
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
            ((PopularActivity)activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
