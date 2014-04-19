package com.watchlistapp.profile;

import com.watchlistapp.R;
import com.watchlistapp.watchlistserver.MovieList;
import com.watchlistapp.watchlistserver.MovieListContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VEINHORN on 06/02/14.
 */
public class ProfileListViewContainer {
    private List<ProfileListViewItem> profileListViewItemArrayList;

    public ProfileListViewContainer() {
        profileListViewItemArrayList = new ArrayList<>();
    }

    public ProfileListViewContainer(MovieListContainer movieListContainer) {
        profileListViewItemArrayList = new ArrayList<>();
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

    public List<ProfileListViewItem> getProfileListViewItemArrayList() {
        return profileListViewItemArrayList;
    }

    public void setProfileListViewItemArrayList(ArrayList<ProfileListViewItem> profileListViewItemArrayList) {
        this.profileListViewItemArrayList = profileListViewItemArrayList;
    }
}
