package com.watchlistapp.authorization;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.watchlistapp.database.WatchListDatabaseHandler;
import com.watchlistapp.utils.MD5Util;

/**
 * Created by VEINHORN on 06/02/14.
 */
public class GAvatar {
    private final static String BASE_URL = "http://www.gravatar.com/avatar/";
    public final static String SMALL = "?s=64";
    public final static String MEDIUM = "?s=180";

    private ImageView userAvatarImageView;
    private Context context;
    private String avatarSize;

    public GAvatar(Context context, ImageView userAvatarImageView, String avatarSize) {
        this.context = context;
        this.userAvatarImageView = userAvatarImageView;
        this.avatarSize = avatarSize;
    }

    public void loadGAvatar() {
        WatchListDatabaseHandler watchListDatabaseHandler = new WatchListDatabaseHandler(context);
        LoggedInUserContainer loggedInUserContainer = watchListDatabaseHandler.getAllUsers();
        LoggedInUser loggedInUser = loggedInUserContainer.searchLastLoggedInUser();
        // If last user doesn't exist
        String url = null;
        if(loggedInUser == null) {
            url = BASE_URL + "123" + avatarSize;
        } else {
            url = BASE_URL + MD5Util.md5Hex(loggedInUser.getEmail()) + avatarSize;
        }
        Picasso.with(context).setDebugging(true);
        Picasso.with(context).load(url).into(userAvatarImageView);
    }
}
