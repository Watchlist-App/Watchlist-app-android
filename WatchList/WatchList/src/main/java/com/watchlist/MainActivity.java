package com.watchlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.watchlist.authorization.LogedInUser;
import com.watchlist.authorization.LogedInUserContainer;
import com.watchlist.authorization.LoginActivity;
import com.watchlist.database.WatchListSQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_recent);

        // There we check when user start app last time
        WatchListSQLiteOpenHelper watchListSQLiteOpenHelper = new WatchListSQLiteOpenHelper(MainActivity.this);
        LogedInUserContainer logedInUserContainer = watchListSQLiteOpenHelper.getAllUsers();
        // If no users in database
        if(logedInUserContainer.getLogedInUserArrayList().isEmpty()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        // If there are users
        LogedInUser logedInUser = logedInUserContainer.getLogedInUserArrayList().get(logedInUserContainer.getLogedInUserArrayList().size() - 1);

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
        } else {
            Intent intent = new Intent(MainActivity.this, RecentActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
