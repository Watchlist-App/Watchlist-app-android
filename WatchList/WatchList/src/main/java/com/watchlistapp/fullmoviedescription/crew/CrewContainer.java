package com.watchlistapp.fullmoviedescription.crew;

import com.watchlistapp.fullmoviedescription.crew.Crew;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 08/01/14.
 */
public class CrewContainer {
    private ArrayList<Crew> crewArrayList;

    public CrewContainer() {
        crewArrayList = new ArrayList<Crew>();
    }

    public ArrayList<Crew> getCrewArrayList() {
        return crewArrayList;
    }

    public void setCrewArrayList(ArrayList<Crew> crewArrayList) {
        this.crewArrayList = crewArrayList;
    }
}
