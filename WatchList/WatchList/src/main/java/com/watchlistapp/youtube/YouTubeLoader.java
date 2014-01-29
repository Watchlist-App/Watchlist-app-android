package com.watchlistapp.youtube;

import android.os.AsyncTask;

import com.google.android.youtube.player.YouTubePlayer;
import com.watchlistapp.utils.RequestsUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by VEINHORN on 12/01/14.
 */
public class YouTubeLoader extends AsyncTask<String, Integer, MovieId> {

    private final static String BASE_URL = "https://www.googleapis.com/youtube/v3/search";

    private final static String TAG_ITEMS = "items";
    private final static String TAG_ID = "id";

    private final static String API_QUERY_TITLE = "q";
    private final static String API_TYPE_TITLE = "type";
    private final static String API_TYPE_VIDEO = "video";
    private final static String API_PART_TITLE = "part";
    private final static String API_PART_SNIPPET = "snippet";
    private final static String API_ORDER_TITLE = "order";
    private final static String API_ORDER_RELEVANCE = "relevance";
    private final static String API_KEY_TITLE = "key";



    private String requestString;
    private YouTubePlayer player;

    public YouTubeLoader(String movieTitle, YouTubePlayer player) {
        this.requestString = movieTitle + " trailer";
        this.player = player;
    }

    @Override
    protected void onPostExecute(MovieId movieId) {
        player.cueVideo(movieId.getVideoId());
    }

    @Override
    protected MovieId doInBackground(String... params) {
        String url = BASE_URL + "?" + API_QUERY_TITLE + "=" + requestString + "&" + API_TYPE_TITLE + "=" + API_TYPE_VIDEO + "&" +
                API_PART_TITLE + "=" + API_PART_SNIPPET + "&" + API_ORDER_TITLE + "=" + API_ORDER_RELEVANCE + "&" + API_KEY_TITLE + "=" + YouTubeDeveloperKey.DEVELOPER_KEY;
        JSONObject jsonObject = RequestsUtil.getJSONObject(url);
        MovieId movieId = parseSearchResults(jsonObject);
        return movieId;
    }

    private MovieId parseSearchResults(JSONObject jsonObject) {
        MovieId movieId = new MovieId();

        try {
            JSONArray searchItemsJsonArray = jsonObject.getJSONArray(TAG_ITEMS);
            for(int i = 0; i < searchItemsJsonArray.length(); i++) {
                JSONObject itemJsonObject = searchItemsJsonArray.getJSONObject(i);
                JSONObject idJsonObject = itemJsonObject.getJSONObject(TAG_ID);
                movieId.setKind(idJsonObject.getString("kind"));
                movieId.setVideoId(idJsonObject.getString("videoId"));
                break;
            }
        } catch(JSONException exception) {
            exception.printStackTrace();
        }

        return movieId;
    }
}
