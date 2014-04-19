package com.watchlistapp.themoviedb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VEINHORN on 25/12/13.
 */

public class Movie {
    private String title;
    private String voteAverage;
    private String voteCount;
    private String status;
    private String budget;
    private String posterPath;
    private String releaseDate;

    private List<String> genres;

    public Movie() {
        genres = new ArrayList<>();
    }

    public Movie(String title, String voteAverage, String voteCount, String status, String budget, String posterPath, String releaseDate, ArrayList<String> genres) {
        this.title = title;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.status = status;
        this.budget = budget;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
}
