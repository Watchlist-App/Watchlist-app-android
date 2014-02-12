package com.watchlistapp.fullmoviedescription.crew;

import android.content.Context;
import android.widget.ImageView;

import com.watchlistapp.fullmoviedescription.actors.ActorAvatarLoader;

/**
 * Created by VEINHORN on 06/02/14.
 */
public class CrewAvatarLoader extends ActorAvatarLoader {
    public CrewAvatarLoader(Context context, ImageView posterImageView, String posterUrl, String posterSize) {
        super(context, posterImageView, posterUrl, posterSize);
    }
}
