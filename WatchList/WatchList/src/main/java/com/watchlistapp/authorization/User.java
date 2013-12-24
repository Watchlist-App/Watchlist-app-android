package com.watchlistapp.authorization;

import com.watchlistapp.watchlistserver.MovieListContainer;

/**
 * Created by VEINHORN on 20/12/13.
 */
public class User {
    private String email;
    private String name;
    private String password;

    private MovieListContainer movieListContainer;

    public User() {
        this.movieListContainer = new MovieListContainer();
    }

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;

        this.movieListContainer = new MovieListContainer();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public MovieListContainer getMovieListContainer() {
        return movieListContainer;
    }

    public void setMovieListContainer(MovieListContainer movieListContainer) {
        this.movieListContainer = movieListContainer;
    }
}
