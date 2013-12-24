package com.watchlistapp.watchlistserver;

/**
 * Created by VEINHORN on 22/12/13.
 */
public class Movie {
    String id;

    public Movie(String id) {
        this.id = id;
    }

    public Movie() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                '}';
    }
}
