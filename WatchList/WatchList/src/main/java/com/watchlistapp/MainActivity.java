package com.watchlistapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.watchlistapp.authorization.LogInActivity;
import com.watchlistapp.authorization.LoggedInUser;
import com.watchlistapp.database.WatchListDatabaseHandler;
import com.watchlistapp.home.HomeActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VEINHORN on 22/12/13.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WatchListDatabaseHandler watchListDatabaseHandler = new WatchListDatabaseHandler(MainActivity.this);
        LoggedInUser loggedInUser = watchListDatabaseHandler.getAllUsers().searchLastLoggedInUser();

        // if no users in the table
        if(loggedInUser == null) {
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(intent);
        } else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
            Date lastLoggedInUserDate = null, currentDate = new Date();

            try {
                lastLoggedInUserDate = dateFormat.parse(loggedInUser.getDate());
            } catch(ParseException exception) {
                exception.printStackTrace();
            }

            long difference = currentDate.getTime() - lastLoggedInUserDate.getTime();
            if(difference < 18000000) { // 5 hours
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        }
        finish();
    }
}
