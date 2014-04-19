package com.watchlistapp.fullmoviedescription.crew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VEINHORN on 08/01/14.
 */
public class CrewItemsContainer {
    private List<CrewItem> crewItemArrayList;

    public CrewItemsContainer() {
        crewItemArrayList = new ArrayList<>();
    }

    public List<CrewItem> getCrewItemArrayList() {
        return crewItemArrayList;
    }

    public void setCrewItemArrayList(ArrayList<CrewItem> crewItemArrayList) {
        this.crewItemArrayList = crewItemArrayList;
    }
}
