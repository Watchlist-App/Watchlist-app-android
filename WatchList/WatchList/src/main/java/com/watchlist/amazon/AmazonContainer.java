package com.watchlist.amazon;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 14/12/13.
 */
public class AmazonContainer {
    private ArrayList<AmazonElement> amazonElementArrayList;

    public AmazonContainer() {
        amazonElementArrayList = new ArrayList<AmazonElement>();
    }

    public AmazonContainer(ArrayList<AmazonElement> amazonElementArrayList) {
        this.amazonElementArrayList = amazonElementArrayList;
    }

    public ArrayList<AmazonElement> getAmazonElementArrayList() {
        return amazonElementArrayList;
    }

    public void setAmazonElementArrayList(ArrayList<AmazonElement> amazonElementArrayList) {
        this.amazonElementArrayList = amazonElementArrayList;
    }
}
