package com.watchlist.searchresults;

/**
 * Created by VEINHORN on 02/12/13.
 */
public class SearchResultsItem {
    private String poster;
    private String title;
    private String releaseDate;
    private String rating;

    public SearchResultsItem(String poster, String releaseDate, String title, String rating) {
        this.poster = poster;
        this.releaseDate = releaseDate;
        this.title = title;
        this.rating = rating;
    }

    public SearchResultsItem() {

    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "SearchResultsItem{" +
                "poster='" + poster + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
