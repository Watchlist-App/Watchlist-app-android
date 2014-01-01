package com.watchlistapp.addmovietolistdialog;

/**
 * Created by VEINHORN on 29/12/13.
 */
public class AddMovieToListDialogListsItem {
    private String title;
    private int icon;
    private boolean radioButtonFlag; // for storing RadioButton last state

    public AddMovieToListDialogListsItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
        this.radioButtonFlag = false;
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

    public boolean getRadioButtonFlag() {
        return radioButtonFlag;
    }

    public void setRadioButtonFlag(boolean radioButtonFlag) {
        this.radioButtonFlag = radioButtonFlag;
    }
}
