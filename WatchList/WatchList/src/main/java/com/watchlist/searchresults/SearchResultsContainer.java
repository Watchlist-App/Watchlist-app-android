package com.watchlist.searchresults;

import com.watchlist.themoviedb.SearchMovieContainer;
import com.watchlist.themoviedb.SearchMovieElement;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 02/12/13.
 */
public class SearchResultsContainer {
    private ArrayList<SearchResultsItem> searchResultsItemArrayList;

    public SearchResultsContainer() {
        searchResultsItemArrayList = new ArrayList<SearchResultsItem>();
    }

    public SearchResultsContainer(SearchMovieContainer searchMovieContainer) {
        searchResultsItemArrayList = new ArrayList<SearchResultsItem>();
        for(SearchMovieElement myMovieElement : searchMovieContainer.getSearchMovieElementArrayList()) {
            SearchResultsItem searchResultsItem = new SearchResultsItem();
            searchResultsItem.setTitle(myMovieElement.getTitle());
            searchResultsItem.setReleaseDate(myMovieElement.getRelease_date());
            searchResultsItem.setRating(myMovieElement.getPopularity());
            searchResultsItemArrayList.add(searchResultsItem);
        }
    }

    public SearchResultsContainer(ArrayList<SearchResultsItem> searchResultsItemArrayList) {
        this.searchResultsItemArrayList = searchResultsItemArrayList;
    }

    public ArrayList<SearchResultsItem> getSearchResultsItemArrayList() {
        return searchResultsItemArrayList;
    }

    public void setSearchResultsItemArrayList(ArrayList<SearchResultsItem> searchResultsItemArrayList) {
        this.searchResultsItemArrayList = searchResultsItemArrayList;
    }

    @Override
    public String toString() {
        String str = "";
        for(int i = 0; i < searchResultsItemArrayList.size(); i++) {
            str += searchResultsItemArrayList.get(i).toString();
        }
        return str;
    }
}
