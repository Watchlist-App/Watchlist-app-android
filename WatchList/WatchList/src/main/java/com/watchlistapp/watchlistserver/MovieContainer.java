package com.watchlistapp.watchlistserver;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 22/12/13.
 */
public class MovieContainer {
    private ArrayList<Movie> movieArrayList;

    public MovieContainer(ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
    }

    public MovieContainer() {
        movieArrayList = new ArrayList<Movie>();
    }

    public ArrayList<Movie> getMovieArrayList() {
        return movieArrayList;
    }

    public void setMovieArrayList(ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
    }
}
