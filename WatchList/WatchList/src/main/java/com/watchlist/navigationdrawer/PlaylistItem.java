package com.watchlist.navigationdrawer;

/**
 * Created by VEINHORN on 26/11/13.
 */
public class PlaylistItem {
    String title;
    Integer movies;
    int icon;

    public PlaylistItem() {

    }

    public PlaylistItem(String title, Integer movies, int icon) {
        this.title = title;
        this.movies = movies;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMovies() {
        return movies;
    }

    public void setMovies(Integer movies) {
        this.movies = movies;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
