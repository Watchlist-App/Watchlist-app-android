package com.watchlistapp.fullmoviedescription.actors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VEINHORN on 07/01/14.
 */
public class ActorItemsContainer {
    private List<ActorItem> actorItemArrayList;

    public ActorItemsContainer() {
        actorItemArrayList = new ArrayList<>();
    }

    public List<ActorItem> getActorItemArrayList() {
        return actorItemArrayList;
    }

    public void setActorItemArrayList(ArrayList<ActorItem> actorItemArrayList) {
        this.actorItemArrayList = actorItemArrayList;
    }
}
