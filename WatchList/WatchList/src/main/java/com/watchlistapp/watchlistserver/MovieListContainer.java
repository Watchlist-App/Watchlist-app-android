package com.watchlistapp.watchlistserver;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 22/12/13.
 */
public class MovieListContainer {
    private ArrayList<MovieList> movieListArrayList;

    public MovieListContainer() {
        movieListArrayList = new ArrayList<MovieList>();
    }

    public MovieList getMovieList(int position) {
        return movieListArrayList.get(position);
    }

    public void addMovieList(MovieList movieList) {
        movieListArrayList.add(movieList);
    }

    public int size() {
        return movieListArrayList.size();
    }

    public ArrayList<MovieList> getMovieListArrayList() {
        return movieListArrayList;
    }
}
