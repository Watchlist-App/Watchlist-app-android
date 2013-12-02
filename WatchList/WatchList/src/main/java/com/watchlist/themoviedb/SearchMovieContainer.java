package com.watchlist.themoviedb;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 02/12/13.
 */
public class SearchMovieContainer {
    ArrayList<SearchMovieElement> searchMovieElementArrayList;

    public SearchMovieContainer() {
        searchMovieElementArrayList = new ArrayList<SearchMovieElement>();
    }

    public SearchMovieContainer(ArrayList<SearchMovieElement> searchMovieElementArrayList) {
        this.searchMovieElementArrayList = searchMovieElementArrayList;
    }

    public ArrayList<SearchMovieElement> getSearchMovieElementArrayList() {
        return searchMovieElementArrayList;
    }

    public void setSearchMovieElementArrayList(ArrayList<SearchMovieElement> searchMovieElementArrayList) {
        this.searchMovieElementArrayList = searchMovieElementArrayList;
    }
}
