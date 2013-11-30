package com.watchlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.watchlist.authorization.LoginActivity;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        //setContentView(R.layout.activity_recent);
        /*
        // There we check when user start app last time
        WatchListSQLiteOpenHelper watchListSQLiteOpenHelper = new WatchListSQLiteOpenHelper(MainActivity.this);
        watchListSQLiteOpenHelper.deleteAllUsers();//test
        LogedInUserContainer logedInUserContainer = watchListSQLiteOpenHelper.getAllUsers();
        // If no users in database
        if(logedInUserContainer.getLogedInUserArrayList().size() == 0) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // If there are users
            LogedInUser logedInUser = null;
            if(logedInUserContainer.getLogedInUserArrayList().size() != 0)
                logedInUser = logedInUserContainer.getLogedInUserArrayList().get(logedInUserContainer.getLogedInUserArrayList().size() - 1);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
            Date lastUserDate = null;
            try {
                lastUserDate = dateFormat.parse(logedInUser.getDate());
            } catch(ParseException exception) {
                exception.printStackTrace();
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
            Date currentDate = new Date();
            long diff = currentDate.getTime() - lastUserDate.getTime();
            Toast toast = Toast.makeText(MainActivity.this, String.valueOf(diff), Toast.LENGTH_LONG);
            //toast.show();
            // if difference more that our
            if(diff > 3600000) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(MainActivity.this, RecentActivity.class);
                startActivity(intent);
                finish();
            }
        }
        */
    }
}
