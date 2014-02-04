package com.watchlistapp.authorization;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.watchlistapp.R;
import com.watchlistapp.database.WatchListDatabaseHandler;

/**
 * Created by VEINHORN on 03/02/14.
 */
public class ProfileActivity extends ActionBarActivity {

    private ImageView userAvatar;
    private TextView userName;
    private TextView userEmail;


    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_background_color)));
        actionBar.setTitle(Html.fromHtml("<b><font color=\"#424242\">" + getString(R.string.profile_activity_title) + "</font></b>"));

        userAvatar = (ImageView)findViewById(R.id.profile_avatar);
        userName = (TextView)findViewById(R.id.profile_name);
        userEmail = (TextView)findViewById(R.id.profile_email);

        WatchListDatabaseHandler watchListDatabaseHandler = new WatchListDatabaseHandler(this);
        LoggedInUserContainer loggedInUserContainer = watchListDatabaseHandler.getAllUsers();
        LoggedInUser loggedInUser = loggedInUserContainer.searchLastLoggedInUser();

        GAvatar gAvatar = new GAvatar(this, userAvatar, GAvatar.MEDIUM);
        gAvatar.execute();
        userName.setText(loggedInUser.getName());
        userEmail.setText(loggedInUser.getEmail());
    }
}
