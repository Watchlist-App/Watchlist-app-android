package com.watchlistapp.amazon;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 22/02/14.
 */
public class AmazonItemsContainer {
    private ArrayList<AmazonItem> amazonItemArrayList;

    public AmazonItemsContainer() {
        amazonItemArrayList = new ArrayList<AmazonItem>();
    }

    public ArrayList<AmazonItem> getAmazonItemArrayList() {
        return amazonItemArrayList;
    }

    public void setAmazonItemArrayList(ArrayList<AmazonItem> amazonItemArrayList) {
        this.amazonItemArrayList = amazonItemArrayList;
    }
}
