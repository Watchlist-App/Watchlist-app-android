package com.watchlistapp.themoviedb;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by VEINHORN on 04/02/14.
 */
public class NewPosterLoader {
    private final static String BASE_URL = "http://image.tmdb.org/t/p/";
    public final static String SMALL = "w92";
    public final static String MEDIUM = "w154";
    public final static String BIG = "w185";
    public final static String DOUBLE_BIG = "w342";
    public final static String LARGE = "w500";
    public final static String ORIGINAL = "original";

    protected Context context;
    protected ImageView posterImageView;
    protected String url;

    public NewPosterLoader(Context context, ImageView posterImageView, String posterUrl, String posterSize) {
        this.context = context;
        this.posterImageView = posterImageView;
        if(posterSize == null) {
            url = BASE_URL + BIG + "/" + posterUrl;
        } else {
            url = BASE_URL + posterSize + "/" + posterUrl;
        }
    }

    public void loadPoster() {
        Picasso.with(context).setDebugging(true);
        Picasso.with(context).load(url).into(posterImageView);
    }
}
