package com.watchlistapp.fullmoviedescription.genres;

import com.watchlistapp.fullmoviedescription.genres.Genre;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 01/01/14.
 */
public class GenreContainer {
    private ArrayList<Genre> genresArrayList;

    public GenreContainer() {
        genresArrayList = new ArrayList<Genre>();
    }

    public ArrayList<Genre> getGenresArrayList() {
        return genresArrayList;
    }

    public void setGenresArrayList(ArrayList<Genre> genresArrayList) {
        this.genresArrayList = genresArrayList;
    }
}
