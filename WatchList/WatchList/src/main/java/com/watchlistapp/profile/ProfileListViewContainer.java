package com.watchlistapp.profile;

import com.watchlistapp.R;
import com.watchlistapp.watchlistserver.MovieList;
import com.watchlistapp.watchlistserver.MovieListContainer;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 06/02/14.
 */
public class ProfileListViewContainer {
    private ArrayList<ProfileListViewItem> profileListViewItemArrayList;

    public ProfileListViewContainer() {
        profileListViewItemArrayList = new ArrayList<ProfileListViewItem>();
    }

    public ProfileListViewContainer(MovieListContainer movieListContainer) {
        profileListViewItemArrayList = new ArrayList<ProfileListViewItem>();
        for(MovieList movieList : movieListContainer.getMovieListArrayList()) {
            if(movieList.getTitle().equals("watchlist")) {
                profileListViewItemArrayList.add(new ProfileListViewItem(R.drawable.watchlistmenu, movieList.getTitle(), Integer.toString(movieList.getMovieContainer().getMovieArrayList().size())));
            } else if(movieList.getTitle().equals("favorites")) {
                profileListViewItemArrayList.add(new ProfileListViewItem(R.drawable.favourite, movieList.getTitle(), Integer.toString(movieList.getMovieContainer().getMovieArrayList().size())));
            } else if(movieList.getTitle().equals("watched")) {
                profileListViewItemArrayList.add(new ProfileListViewItem(R.drawable.watched, movieList.getTitle(), Integer.toString(movieList.getMovieContainer().getMovieArrayList().size())));
            } else {
                profileListViewItemArrayList.add(new ProfileListViewItem(R.drawable.mylists, movieList.getTitle(), Integer.toString(movieList.getMovieContainer().getMovieArrayList().size())));
            }
        }
    }

    public ArrayList<ProfileListViewItem> getProfileListViewItemArrayList() {
        return profileListViewItemArrayList;
    }

    public void setProfileListViewItemArrayList(ArrayList<ProfileListViewItem> profileListViewItemArrayList) {
        this.profileListViewItemArrayList = profileListViewItemArrayList;
    }
}
