package com.watchlistapp.profile;

/**
 * Created by VEINHORN on 06/02/14.
 */
public class ProfileListViewItem {
    private int icon;
    private String listTitle;
    private String moviesNumber;

    public ProfileListViewItem() {
    }

    public ProfileListViewItem(int icon, String listTitle, String moviesNumber) {
        this.icon = icon;
        this.listTitle = listTitle;
        this.moviesNumber = moviesNumber;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public String getMoviesNumber() {
        return moviesNumber;
    }

    public void setMoviesNumber(String moviesNumber) {
        this.moviesNumber = moviesNumber;
    }
}
