package com.watchlist.authorization;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 29/11/13.
 */
public class LogedInUserContainer {
    private ArrayList<LogedInUser> logedInUserArrayList;

    public LogedInUserContainer() {
        logedInUserArrayList = new ArrayList<LogedInUser>();
    }

    public LogedInUserContainer(ArrayList<LogedInUser> logedInUserArrayList) {
        this.logedInUserArrayList = logedInUserArrayList;
    }

    public ArrayList<LogedInUser> getLogedInUserArrayList() {
        return logedInUserArrayList;
    }

    public void setLogedInUserArrayList(ArrayList<LogedInUser> logedInUserArrayList) {
        this.logedInUserArrayList = logedInUserArrayList;
    }

    public boolean isSuchUser(LogedInUser user) {
        for(LogedInUser myuser : logedInUserArrayList) {
            if(myuser.getName().equals(user.getName()) && myuser.getEmail().equals(user.getEmail()) && myuser.getPassword().equals(user.getPassword()) && myuser.getDate().equals(user.getDate()))
                return true;
        }
        return false;
    }
}
