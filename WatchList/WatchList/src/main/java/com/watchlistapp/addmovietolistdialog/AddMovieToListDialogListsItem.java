package com.watchlistapp.addmovietolistdialog;

/**
 * Created by VEINHORN on 29/12/13.
 */
public class AddMovieToListDialogListsItem {
    private String title;
    private int icon;

    public AddMovieToListDialogListsItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public AddMovieToListDialogListsItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
