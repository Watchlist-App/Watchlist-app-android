package com.watchlistapp.itunes;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 23/02/14.
 */
public class ITunesItemsContainer {
    private ArrayList<ITunesItem> iTunesItemArrayList;

    public ITunesItemsContainer() {
        iTunesItemArrayList = new ArrayList<ITunesItem>();
    }

    public ArrayList<ITunesItem> getiTunesItemArrayList() {
        return iTunesItemArrayList;
    }

    public void setiTunesItemArrayList(ArrayList<ITunesItem> iTunesItemArrayList) {
        this.iTunesItemArrayList = iTunesItemArrayList;
    }
}
