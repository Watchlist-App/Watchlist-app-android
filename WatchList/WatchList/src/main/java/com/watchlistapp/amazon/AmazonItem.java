package com.watchlistapp.amazon;

/**
 * Created by VEINHORN on 22/02/14.
 */
public class AmazonItem {
    private String title;
    private String price;
    private String detailPageUrl;
    private String posterUrl;

    public AmazonItem() {

    }

    public AmazonItem(String title, String price, String detailPageUrl, String posterUrl) {
        this.title = title;
        this.price = price;
        this.detailPageUrl = detailPageUrl;
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
