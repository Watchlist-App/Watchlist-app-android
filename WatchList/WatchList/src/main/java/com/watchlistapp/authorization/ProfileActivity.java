package com.watchlistapp.authorization;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.watchlistapp.R;
import com.watchlistapp.database.WatchListDatabaseHandler;

/**
 * Created by VEINHORN on 03/02/14.
 */
public class ProfileActivity extends ActionBarActivity {

    private ImageView userAvatarImageView;
    private TextView userNameTextView;
    private TextView userEmailTextView;
    private TextView listsNumberTextView;
    private TextView moviesNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_background_color)));
        actionBar.setTitle(Html.fromHtml("<b><font color=\"#424242\">" + getString(R.string.profile_activity_title) + "</font></b>"));

        userAvatarImageView = (ImageView)findViewById(R.id.profile_avatar);
        userNameTextView = (TextView)findViewById(R.id.profile_name);
        userEmailTextView = (TextView)findViewById(R.id.profile_email);
        listsNumberTextView = (TextView)findViewById(R.id.profile_lists_number);
        moviesNumberTextView = (TextView)findViewById(R.id.profile_movies_number);

        WatchListDatabaseHandler watchListDatabaseHandler = new WatchListDatabaseHandler(this);
        LoggedInUserContainer loggedInUserContainer = watchListDatabaseHandler.getAllUsers();
        LoggedInUser loggedInUser = loggedInUserContainer.searchLastLoggedInUser();
        String listsNumber = new Integer(watchListDatabaseHandler.getPlaylistsNumber()).toString();
        String moviesNumber = new Integer(watchListDatabaseHandler.getMoviesNumber()).toString();

        userAvatarImageView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        new GAvatar(this, userAvatarImageView, GAvatar.MEDIUM).loadGAvatar();
        userNameTextView.setText(loggedInUser.getName());
        userEmailTextView.setText(loggedInUser.getEmail());
        listsNumberTextView.setText(listsNumber);
        moviesNumberTextView.setText(moviesNumber);
    }
}
