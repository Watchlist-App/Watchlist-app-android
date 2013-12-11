package com.watchlist;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 11/12/13.
 */
public class NavigationDrawerItemContainer {
    private ArrayList<NavigationDrawerItem> navigationDrawerItemArrayList;

    public NavigationDrawerItemContainer() {
        navigationDrawerItemArrayList = new ArrayList<NavigationDrawerItem>();
    }

    public ArrayList<NavigationDrawerItem> getNavigationDrawerItemArrayList() {
        return navigationDrawerItemArrayList;
    }

    public void setNavigationDrawerItemArrayList(ArrayList<NavigationDrawerItem> navigationDrawerItemArrayList) {
        this.navigationDrawerItemArrayList = navigationDrawerItemArrayList;
    }
}
