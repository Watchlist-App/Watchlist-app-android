package com.watchlistapp.authorization;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 25/11/13.
 */
public class UserContainer {
    private ArrayList<User> usersArrayList;

    public UserContainer() {
        usersArrayList = new ArrayList<User>();
    }

    public UserContainer(ArrayList<User> usersArrayList) {
        this.usersArrayList = usersArrayList;
    }

    public ArrayList<User> getUsersArrayList() {
        return usersArrayList;
    }

    public void setUsersArrayList(ArrayList<User> usersArrayList) {
        this.usersArrayList = usersArrayList;
    }

    public boolean isSuchUser(User user) {
        for(User myuser : usersArrayList) {
            if(myuser.getName().equals(user.getName()) && myuser.getEmail().equals(user.getEmail()) && myuser.getPassword().equals(user.getPassword()))
                return true;
        }
        return false;
    }
}
