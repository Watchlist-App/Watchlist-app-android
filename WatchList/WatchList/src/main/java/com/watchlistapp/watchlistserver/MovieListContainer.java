package com.watchlistapp.watchlistserver;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 22/12/13.
 */
public class MovieListContainer {
    private ArrayList<MovieList> movieListArrayList;

    public MovieListContainer(ArrayList<MovieList> movieListArrayList) {
        this.movieListArrayList = movieListArrayList;
    }

    public MovieListContainer() {
        movieListArrayList = new ArrayList<MovieList>();
    }

    public ArrayList<MovieList> getMovieListArrayList() {
        return movieListArrayList;
    }

    public void setMovieListArrayList(ArrayList<MovieList> movieListArrayList) {
        this.movieListArrayList = movieListArrayList;
    }
}
