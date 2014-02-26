package com.watchlistapp.itunes;

/**
 * Created by VEINHORN on 23/02/14.
 */
public class ITunesItem {
    private String title; // trackname on itunes
    private String posterUrl; // artworkUrl100 on itunes
    private String price; // trackPrice on itunes
    private String detailPageUrl; // trackViewUrl on itunes

    public ITunesItem(String title, String posterUrl, String price, String detailPageUrl) {
        this.title = title;
        this.posterUrl = posterUrl;
        this.price = price;
        this.detailPageUrl = detailPageUrl;
    }

    public ITunesItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetailPageUrl() {
        return detailPageUrl;
    }

    public void setDetailPageUrl(String detailPageUrl) {
        this.detailPageUrl = detailPageUrl;
    }
}
