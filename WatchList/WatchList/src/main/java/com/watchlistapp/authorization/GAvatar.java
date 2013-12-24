package com.watchlistapp.authorization;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.watchlistapp.MD5Util;
import com.watchlistapp.database.WatchListDatabaseHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by VEINHORN on 18/12/13.
 */
public class GAvatar extends AsyncTask<String, Integer, Bitmap> {

    private final static String BASE_URL = "http://www.gravatar.com/avatar/";

    private ImageView userAvatarImageView;
    private Context context;

    public GAvatar(Context context, ImageView userAvatarImageView) {
        this.context = context;
        this.userAvatarImageView = userAvatarImageView;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        userAvatarImageView.setImageBitmap(bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        URL url = null;
        try {
            WatchListDatabaseHandler watchListDatabaseHandler = new WatchListDatabaseHandler(context);
            LoggedInUserContainer loggedInUserContainer = watchListDatabaseHandler.getAllUsers();
            LoggedInUser loggedInUser = loggedInUserContainer.searchLastLoggedInUser();
            // If last user doesn't exist
            if(loggedInUser == null) {
                url = new URL(BASE_URL + "123?s=64");
            } else {
                String myUrl = BASE_URL + MD5Util.md5Hex(loggedInUser.getEmail()) + "?s=64";
                url = new URL(myUrl);
            }
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch(MalformedURLException exception) {
            exception.printStackTrace();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
        return bitmap;
    }
}
