package com.watchlistapp.fullmoviedescription.crew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VEINHORN on 08/01/14.
 */
public class CrewContainer {
    private List<Crew> crewArrayList;

    public CrewContainer() {
        crewArrayList = new ArrayList<>();
    }

    public List<Crew> getCrewArrayList() {
        return crewArrayList;
    }

    public void setCrewArrayList(ArrayList<Crew> crewArrayList) {
        this.crewArrayList = crewArrayList;
    }
}
