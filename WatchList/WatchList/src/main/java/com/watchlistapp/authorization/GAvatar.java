package com.watchlistapp.authorization;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by VEINHORN on 18/12/13.
 */
public class GAvatar extends AsyncTask<String, Integer, Bitmap> {

    private final static String BASE_URL = "http://www.gravatar.com/avatar/";

    private ImageView userAvatarImageView;

    public GAvatar(ImageView userAvatarImageView) {
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

            url = new URL("http://www.gravatar.com/avatar/123?s=64");
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch(MalformedURLException exception) {
            exception.printStackTrace();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
        return bitmap;
    }
}
