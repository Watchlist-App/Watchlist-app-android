package com.watchlistapp.fullmoviedescription;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 26/12/13.
 */
public class MovieDescription {
    private String title;
    private String voteAverage;
    private String voteCount;
    private String status;
    private String tagline;
    private String runtime;
    private String releaseDate;
    private String posterPath;
    private String overview;
    private String homepage;
    private String budget;
    private String backdropPath;

    private ArrayList<String> genres;

    public MovieDescription() {
        genres = new ArrayList<String>();
    }

    public MovieDescription(String title, String voteAverage, String voteCount, String status, String tagline, String runtime, String releaseDate, String posterPath, String overview, String homepage, String budget, String backdropPath, ArrayList<String> genres) {
        this.title = title;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.status = status;
        this.tagline = tagline;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.overview = overview;
        this.homepage = homepage;
        this.budget = budget;
        this.backdropPath = backdropPath;
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

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
}
