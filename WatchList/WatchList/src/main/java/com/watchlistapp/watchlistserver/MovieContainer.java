package com.watchlistapp.watchlistserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VEINHORN on 22/12/13.
 */
public class MovieContainer {
    private List<Movie> movieArrayList;

    public MovieContainer() {
        movieArrayList = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        movieArrayList.add(movie);
    }

    public Movie getMovie(int position) {
        return movieArrayList.get(position);
    }

    public List<Movie> getMovieArrayList() {
        return movieArrayList;
    }
}
