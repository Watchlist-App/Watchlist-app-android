package com.watchlistapp.addmovietolistdialog;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 29/12/13.
 */
public class AddMovieToListDialogListsItemContainer {
    private ArrayList<AddMovieToListDialogListsItem> addMovieToListDialogListsItemArrayList;

    public AddMovieToListDialogListsItemContainer() {
        addMovieToListDialogListsItemArrayList = new ArrayList<AddMovieToListDialogListsItem>();
    }

    public ArrayList<AddMovieToListDialogListsItem> getAddMovieToListDialogListsItemArrayList() {
        return addMovieToListDialogListsItemArrayList;
    }

    public void setAddMovieToListDialogListsItemArrayList(ArrayList<AddMovieToListDialogListsItem> addMovieToListDialogListsItemArrayList) {
        this.addMovieToListDialogListsItemArrayList = addMovieToListDialogListsItemArrayList;
    }

    // This method save state of radio button
    public void saveState(int position) {
        for(int i = 0; i < addMovieToListDialogListsItemArrayList.size(); i++) {
            addMovieToListDialogListsItemArrayList.get(i).setRadioButtonFlag(false);
        }
        addMovieToListDialogListsItemArrayList.get(position).setRadioButtonFlag(true);
    }

    // This method returns the last position of radio button, or -1 if radio button wasn't selected
    // The counting beginning from 0
    public int getLastState() {
        for(int i = 0; i < addMovieToListDialogListsItemArrayList.size(); i++) {
            if(addMovieToListDialogListsItemArrayList.get(i).getRadioButtonFlag()) {
                return i;
            }
        }
        return -1;
    }
}
