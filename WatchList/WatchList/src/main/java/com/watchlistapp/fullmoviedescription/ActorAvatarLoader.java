package com.watchlistapp.fullmoviedescription;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by VEINHORN on 07/01/14.
 */
public class ActorAvatarLoader extends AsyncTask<String, Integer, Bitmap> {

    private final static String BASE_URL = "http://image.tmdb.org/t/p/";

    public final static String SMALL = "w92";
    public final static String MEDIUM = "w154";
    public final static String BIG = "w185"; //175*278 ->> original
    public final static String DOUBLE_BIG = "w342";
    public final static String LARGE = "w500";
    public final static String ORIGINAL = "original";

    private String url;
    private ActorItemsListAdapter actorItemsListAdapter;
    private ActorItemsContainer actorItemsContainer;
    private int position;
    private String posterSize;

    public ActorAvatarLoader(ActorItemsListAdapter actorItemsListAdapter, ActorItemsContainer actorItemsContainer,int position, String posterUrl, String posterSize) {
        this.actorItemsListAdapter = actorItemsListAdapter;
        this.actorItemsContainer = actorItemsContainer;
        this.position = position;
        this.posterSize = posterSize;

        if(posterSize == null) {
            url = BASE_URL + BIG + "/" + posterUrl;
        } else {
            url = BASE_URL + posterSize + "/" + posterUrl;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        actorItemsListAdapter.notifyDataSetChanged();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        URL url = null;
        try {
            url = new URL(this.url);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            if(posterSize.equals(BIG)) {
                bitmap = bitmap.createScaledBitmap(bitmap, 175, 280, false);
            } else if(posterSize.equals(SMALL)) {
                bitmap = bitmap.createScaledBitmap(bitmap, 175, 280, false);
            }
            actorItemsContainer.getActorItemArrayList().get(position).setActorAvatar(bitmap);
        } catch(MalformedURLException exception) {
            exception.printStackTrace();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
        return bitmap;
    }
}
