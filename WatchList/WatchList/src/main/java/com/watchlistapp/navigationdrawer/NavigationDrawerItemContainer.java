package com.watchlistapp.navigationdrawer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VEINHORN on 11/12/13.
 */
public class NavigationDrawerItemContainer {
    private List<NavigationDrawerItem> navigationDrawerItemArrayList;

    public NavigationDrawerItemContainer() {
        navigationDrawerItemArrayList = new ArrayList<>();
    }

    public List<NavigationDrawerItem> getNavigationDrawerItemArrayList() {
        return navigationDrawerItemArrayList;
    }

    public void setNavigationDrawerItemArrayList(ArrayList<NavigationDrawerItem> navigationDrawerItemArrayList) {
        this.navigationDrawerItemArrayList = navigationDrawerItemArrayList;
    }
}
