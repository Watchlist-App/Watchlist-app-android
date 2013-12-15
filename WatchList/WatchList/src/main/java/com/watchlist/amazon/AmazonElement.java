package com.watchlist.amazon;

/**
 * Created by VEINHORN on 14/12/13.
 */
public class AmazonElement {

    private String detailPageUrl;
    private String posterUrl;

    private String smallPoster;
    private String mediumPoster;
    private String largePoster;

    private String title;

    public AmazonElement() {

    }

    public AmazonElement(String detailPageUrl, String posterUrl, String smallPoster, String mediumPoster, String largePoster, String title) {
        this.detailPageUrl = detailPageUrl;
        this.posterUrl = posterUrl;
        this.smallPoster = smallPoster;
        this.mediumPoster = mediumPoster;
        this.largePoster = largePoster;
        this.title = title;
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

    public String getSmallPoster() {
        return smallPoster;
    }

    public void setSmallPoster(String smallPoster) {
        this.smallPoster = smallPoster;
    }

    public String getMediumPoster() {
        return mediumPoster;
    }

    public void setMediumPoster(String mediumPoster) {
        this.mediumPoster = mediumPoster;
    }

    public String getLargePoster() {
        return largePoster;
    }

    public void setLargePoster(String largePoster) {
        this.largePoster = largePoster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
