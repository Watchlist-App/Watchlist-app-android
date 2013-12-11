package com.watchlist;

/**
 * Created by VEINHORN on 11/12/13.
 */
public class NavigationDrawerItem {
    String title;
    int iconId;

    public NavigationDrawerItem() {
    }

    public NavigationDrawerItem(String title, int iconId) {
        this.title = title;
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
