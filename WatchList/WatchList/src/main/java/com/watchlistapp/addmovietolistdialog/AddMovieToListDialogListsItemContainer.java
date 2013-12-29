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
}
