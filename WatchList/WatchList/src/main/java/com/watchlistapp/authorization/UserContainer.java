package com.watchlistapp.authorization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VEINHORN on 25/11/13.
 */
public class UserContainer {
    private List<User> usersArrayList;

    public UserContainer() {
        usersArrayList = new ArrayList<>();
    }

    public UserContainer(ArrayList<User> usersArrayList) {
        this.usersArrayList = usersArrayList;
    }

    public List<User> getUsersArrayList() {
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
