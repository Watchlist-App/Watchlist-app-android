package com.watchlistapp.themoviedb;

/**
 * Created by VEINHORN on 02/12/13.
 */
public class SearchMovieElement {
    private String adult;
    private String backdrop_path;
    private String id;
    private String original_title;
    private String release_date;
    private String poster_path;
    private String popularity;
    private String title;
    private String vote_average;
    private String vote_count;

    public SearchMovieElement() {
    }

    public SearchMovieElement(String adult, String backdrop_path, String id, String original_title, String release_date, String poster_path, String popularity, String title, String vote_average, String vote_count) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.id = id;
        this.original_title = original_title;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.title = title;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    @Override
    public String toString() {
        return "SearchMovieElement{" +
                "adult='" + adult + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", id='" + id + '\'' +
                ", original_title='" + original_title + '\'' +
                ", release_date='" + release_date + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", popularity='" + popularity + '\'' +
                ", title='" + title + '\'' +
                ", vote_average='" + vote_average + '\'' +
                ", vote_count='" + vote_count + '\'' +
                '}';
    }
}
