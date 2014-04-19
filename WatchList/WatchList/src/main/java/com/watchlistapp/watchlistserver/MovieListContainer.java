package com.watchlistapp.watchlistserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VEINHORN on 22/12/13.
 */
public class MovieListContainer {
    private List<MovieList> movieListArrayList;

    public MovieListContainer() {
        movieListArrayList = new ArrayList<>();
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

    public List<MovieList> getMovieListArrayList() {
        return movieListArrayList;
    }
}
