package com.watchlistapp.itunes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VEINHORN on 23/02/14.
 */
public class ITunesItemsContainer {
    private List<ITunesItem> iTunesItemArrayList;

    public ITunesItemsContainer() {
        iTunesItemArrayList = new ArrayList<>();
    }

    public void addiTunesItem(ITunesItem iTunesItem) {
        iTunesItemArrayList.add(iTunesItem);
    }

    public ITunesItem getiTunesItem(int position) {
        return iTunesItemArrayList.get(position);
    }

    public int size() {
        return iTunesItemArrayList.size();
    }
}
