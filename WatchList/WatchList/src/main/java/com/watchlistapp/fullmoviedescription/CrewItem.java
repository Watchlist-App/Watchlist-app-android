package com.watchlistapp.fullmoviedescription;

import android.graphics.Bitmap;

/**
 * Created by VEINHORN on 08/01/14.
 */
public class CrewItem extends Crew {
    private Bitmap crewAvatar;

    public CrewItem() {
        super();
    }

    public Bitmap getCrewAvatar() {
        return crewAvatar;
    }

    public void setCrewAvatar(Bitmap crewAvatar) {
        this.crewAvatar = crewAvatar;
    }
}
