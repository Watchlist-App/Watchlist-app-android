package com.watchlistapp.themoviedb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VEINHORN on 02/12/13.
 */
public class SearchMovieContainer {
    List<SearchMovieElement> searchMovieElementArrayList;

    public SearchMovieContainer() {
        searchMovieElementArrayList = new ArrayList<>();
    }

    public SearchMovieContainer(ArrayList<SearchMovieElement> searchMovieElementArrayList) {
        this.searchMovieElementArrayList = searchMovieElementArrayList;
    }

    public List<SearchMovieElement> getSearchMovieElementArrayList() {
        return searchMovieElementArrayList;
    }

    public void setSearchMovieElementArrayList(ArrayList<SearchMovieElement> searchMovieElementArrayList) {
        this.searchMovieElementArrayList = searchMovieElementArrayList;
    }
}
