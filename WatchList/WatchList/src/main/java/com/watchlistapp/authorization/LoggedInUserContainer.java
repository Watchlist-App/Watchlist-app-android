package com.watchlistapp.authorization;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by VEINHORN on 22/12/13.
 */
public class LoggedInUserContainer {
    private List<LoggedInUser> loggedInUserArrayList;

    public LoggedInUserContainer() {
        loggedInUserArrayList = new ArrayList<>();
    }

    public LoggedInUserContainer(ArrayList<LoggedInUser> loggedInUserArrayList) {
        this.loggedInUserArrayList = loggedInUserArrayList;
    }

    public List<LoggedInUser> getLoggedInUserArrayList() {
        return loggedInUserArrayList;
    }

    public void setLoggedInUserArrayList(ArrayList<LoggedInUser> loggedInUserArrayList) {
        this.loggedInUserArrayList = loggedInUserArrayList;
    }


    // This method returns last logged in user, returns null if no users in the container
    public LoggedInUser searchLastLoggedInUser() {
        LoggedInUser loggedInUser = null;
        if(loggedInUserArrayList.isEmpty()) {
            return loggedInUser;
        } else {
            loggedInUser = loggedInUserArrayList.get(0);
            for(LoggedInUser myUser : loggedInUserArrayList) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
                Date currentUserDate = null, nextUserDate = null;

                try {
                    currentUserDate = dateFormat.parse(loggedInUser.getDate());
                    nextUserDate = dateFormat.parse(myUser.getDate());
                }catch(ParseException exception) {
                    exception.printStackTrace();
                }

                if(currentUserDate.getTime() < nextUserDate.getTime()) {
                    loggedInUser = myUser;
                }
            }
            return loggedInUser;
        }
    }
}
