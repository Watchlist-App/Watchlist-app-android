package com.watchlistapp.itunes;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by VEINHORN on 23/02/14.
 */
public class ITunesLoader {
    private final static String BASE_URL = "https://itunes.apple.com/search?term=";
    private final static String API_MEDIA = "&media=movie";

    private Context context;
    private String movieTitle;
    private ITunesItemsContainer iTunesItemsContainer;
    private ITunesItemsAdapter iTunesItemsAdapter;

    public ITunesLoader(Context context, String movieTitle, ITunesItemsContainer iTunesItemsContainer, ITunesItemsAdapter iTunesItemsAdapter) {
        this.context = context;
        this.movieTitle = movieTitle;
        this.iTunesItemsContainer = iTunesItemsContainer;
        this.iTunesItemsAdapter = iTunesItemsAdapter;
    }

    public void loadDataFromITunes() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = BASE_URL + movieTitle.replaceAll(" ", "%20") + API_MEDIA;
        requestQueue.add(new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                parseJSONObject(jsonObject);
                iTunesItemsAdapter.notifyDataSetChanged();
            }
        }, null));
    }

    private void parseJSONObject(JSONObject jsonObject) {
        try {
            JSONArray resultsJsonArray = jsonObject.getJSONArray("results");
            int size = resultsJsonArray.length();
            for(int i = 0; i < size; i++) {
                JSONObject resultJsonObject = resultsJsonArray.getJSONObject(i);
                String title = resultJsonObject.getString("trackName");
                String detailPageUrl = resultJsonObject.getString("trackViewUrl");
                String price = "$" + resultJsonObject.getString("trackPrice");
                String posterUrl = resultJsonObject.getString("artworkUrl100").replace("100x100", "200x200");
                iTunesItemsContainer.getiTunesItemArrayList().add(new ITunesItem(title, posterUrl, price, detailPageUrl));
            }
        } catch(JSONException exception) {
            exception.printStackTrace();
        }
    }
}
