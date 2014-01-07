package com.watchlistapp.fullmoviedescription;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 07/01/14.
 */
public class ActorItemsContainer {
    private ArrayList<ActorItem> actorItemArrayList;

    public ActorItemsContainer() {
        actorItemArrayList = new ArrayList<ActorItem>();
    }

    public ArrayList<ActorItem> getActorItemArrayList() {
        return actorItemArrayList;
    }

    public void setActorItemArrayList(ArrayList<ActorItem> actorItemArrayList) {
        this.actorItemArrayList = actorItemArrayList;
    }
}
