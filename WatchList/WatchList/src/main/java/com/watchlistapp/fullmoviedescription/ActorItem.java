package com.watchlistapp.fullmoviedescription;

import android.graphics.Bitmap;

/**
 * Created by VEINHORN on 07/01/14.
 */
public class ActorItem extends Actor {
    private Bitmap actorAvatar;

    public ActorItem() {
        super();
    }

    public Bitmap getActorAvatar() {
        return actorAvatar;
    }

    public void setActorAvatar(Bitmap actorAvatar) {
        this.actorAvatar = actorAvatar;
    }
}
