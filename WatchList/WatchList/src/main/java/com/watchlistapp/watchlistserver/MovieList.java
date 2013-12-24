package com.watchlistapp.watchlistserver;

/**
 * Created by VEINHORN on 22/12/13.
 */
public class MovieList {
    private String title;
    private MovieContainer movieContainer;


    public MovieList(String title) {
        this.title = title;
    }

    public MovieList() {
        this.movieContainer = new MovieContainer();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MovieList{" +
                "title='" + title + '\'' +
                '}';
    }

    public MovieContainer getMovieContainer() {
        return movieContainer;
    }

    public void setMovieContainer(MovieContainer movieContainer) {
        this.movieContainer = movieContainer;
    }
}
