package com.watchlistapp.fullmoviedescription;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by VEINHORN on 08/01/14.
 */

public class CrewAvatarLoader extends AsyncTask<String, Integer, Bitmap> {

    private final static String BASE_URL = "http://image.tmdb.org/t/p/";

    public final static String SMALL = "w92";
    public final static String MEDIUM = "w154";
    public final static String BIG = "w185";
    public final static String DOUBLE_BIG = "w342";
    public final static String LARGE = "w500";
    public final static String ORIGINAL = "original";

    private String url;
    private CrewItemsListAdapter crewItemsListAdapter;
    private CrewItemsContainer crewItemsContainer;
    private int position;

    public CrewAvatarLoader(CrewItemsListAdapter crewItemsListAdapter, CrewItemsContainer crewItemsContainer,int position, String posterUrl, String posterSize) {
        this.crewItemsListAdapter = crewItemsListAdapter;
        this.crewItemsContainer = crewItemsContainer;
        this.position = position;

        if(posterSize == null) {
            url = BASE_URL + BIG + "/" + posterUrl;
        } else {
            url = BASE_URL + posterSize + "/" + posterUrl;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        crewItemsListAdapter.notifyDataSetChanged();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        URL url = null;
        try {
            url = new URL(this.url);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            crewItemsContainer.getCrewItemArrayList().get(position).setCrewAvatar(bitmap);
        } catch(MalformedURLException exception) {
            exception.printStackTrace();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
        return bitmap;
    }
}
