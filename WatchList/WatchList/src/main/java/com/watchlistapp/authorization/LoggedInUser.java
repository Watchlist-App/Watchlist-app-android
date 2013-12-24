package com.watchlistapp.authorization;

import com.watchlistapp.authorization.User;

/**
 * Created by VEINHORN on 22/12/13.
 */
public class LoggedInUser extends User {

    private String date;

    public LoggedInUser() {
        super();
    }

    public LoggedInUser(User user, String date) {
        this.date = date;
        this.setEmail(user.getEmail());
        this.setName(user.getName());
        this.setPassword(user.getPassword());
        this.setMovieListContainer(user.getMovieListContainer());
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + "LoggedInUser{" +
                "date='" + date + '\'' +
                '}';
    }
}
