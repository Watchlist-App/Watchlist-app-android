package com.watchlistapp.watchlistserver;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 22/12/13.
 */
public class MovieContainer {
    private ArrayList<Movie> movieArrayList;

    public MovieContainer() {
        movieArrayList = new ArrayList<Movie>();
    }

    public void addMovie(Movie movie) {
        movieArrayList.add(movie);
    }

    public Movie getMovie(int position) {
        return movieArrayList.get(position);
    }

    public ArrayList<Movie> getMovieArrayList() {
        return movieArrayList;
    }
}
