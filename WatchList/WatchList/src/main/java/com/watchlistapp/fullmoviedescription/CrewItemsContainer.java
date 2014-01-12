package com.watchlistapp.fullmoviedescription;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 08/01/14.
 */
public class CrewItemsContainer {
    private ArrayList<CrewItem> crewItemArrayList;

    public CrewItemsContainer() {
        crewItemArrayList = new ArrayList<CrewItem>();
    }

    public ArrayList<CrewItem> getCrewItemArrayList() {
        return crewItemArrayList;
    }

    public void setCrewItemArrayList(ArrayList<CrewItem> crewItemArrayList) {
        this.crewItemArrayList = crewItemArrayList;
    }
}
