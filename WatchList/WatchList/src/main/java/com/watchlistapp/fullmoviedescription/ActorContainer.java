package com.watchlistapp.fullmoviedescription;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 07/01/14.
 */
public class ActorContainer {
    private ArrayList<Actor> actorArrayList;

    public ActorContainer() {
        actorArrayList = new ArrayList<Actor>();
    }

    public ArrayList<Actor> getActorArrayList() {
        return actorArrayList;
    }

    public void setActorArrayList(ArrayList<Actor> actorArrayList) {
        this.actorArrayList = actorArrayList;
    }
}
