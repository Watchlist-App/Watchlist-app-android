package com.watchlistapp.fullmoviedescription.actors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VEINHORN on 07/01/14.
 */
public class ActorContainer {
    private List<Actor> actorArrayList;

    public ActorContainer() {
        actorArrayList = new ArrayList<>();
    }

    public List<Actor> getActorArrayList() {
        return actorArrayList;
    }

    public void setActorArrayList(ArrayList<Actor> actorArrayList) {
        this.actorArrayList = actorArrayList;
    }
}
