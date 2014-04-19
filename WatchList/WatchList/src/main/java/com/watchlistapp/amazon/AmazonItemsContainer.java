package com.watchlistapp.amazon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VEINHORN on 22/02/14.
 */
public class AmazonItemsContainer {
    private List<AmazonItem> amazonItemArrayList;

    public AmazonItemsContainer() {
        amazonItemArrayList = new ArrayList<AmazonItem>();
    }

    public List<AmazonItem> getAmazonItemArrayList() {
        return amazonItemArrayList;
    }

    public void setAmazonItemArrayList(ArrayList<AmazonItem> amazonItemArrayList) {
        this.amazonItemArrayList = amazonItemArrayList;
    }
}
