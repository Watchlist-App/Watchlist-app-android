package com.watchlistapp.fullmoviedescription;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.watchlistapp.themoviedb.NewPosterLoader;

/**
 * Created by VEINHORN on 05/02/14.
 */
public class NewActorAvatarLoader extends NewPosterLoader {
    public NewActorAvatarLoader(Context context, ImageView posterImageView, String posterUrl, String posterSize) {
        super(context, posterImageView, posterUrl, posterSize);
    }

    public void loadPoster() {
        Picasso.with(context).setDebugging(true);
        Picasso.with(context).load(url).resize(175, 280).into(posterImageView); // 175 * 280 is the base size
    }
}
