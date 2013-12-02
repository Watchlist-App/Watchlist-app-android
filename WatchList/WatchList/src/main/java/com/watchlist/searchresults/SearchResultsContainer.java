package com.watchlist.searchresults;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 02/12/13.
 */
public class SearchResultsContainer {
    private ArrayList<SearchResultsItem> searchResultsItemArrayList;

    public SearchResultsContainer() {
        searchResultsItemArrayList = new ArrayList<SearchResultsItem>();
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
}
