package com.watchlist.themoviedb;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.watchlist.comingsoon.ComingSoonContainer;
import com.watchlist.comingsoon.ComingSoonItem;
import com.watchlist.comingsoon.ComingSoonItemAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by VEINHORN on 11/12/13.
 */

public class ComingSoon extends AsyncTask<String, Integer, SearchMovieContainer> {

    private final static String BASE_URL = "http://api.themoviedb.org/3/movie/upcoming";
    private final static String API_KEY = "2b7854ef68a3c274a0f804c031285c46";
    private final static String API_PAGE_TITLE = "page";
    private final static String API_KEY_TITLE = "api_key";
    private final static String API_TOTAL_PAGES_TITLE = "total_pages";

    private final static String API_RESULTS_TITLE = "results";

    private final static String API_RESULTS_ADULT_TITLE = "adult";
    private final static String API_RESULTS_BACKDROP_PATH_TITLE = "backdrop_path";
    private final static String API_RESULTS_ID_TITLE = "id";
    private final static String API_RESULTS_ORIGINAL_TITLE_TITLE = "original_title";
    private final static String API_RESULTS_RELEASE_DATE_TITLE = "release_date";
    private final static String API_RESULTS_POSTER_PATH_TITLE = "poster_path";
    private final static String API_RESULTS_POPULARITY_TITLE = "popularity";
    private final static String API_RESULTS_TITLE_TITLE = "title";
    private final static String API_RESULTS_VOTE_AVERAGE_TITLE = "vote_average";
    private final static String API_RESULTS_VOTE_COUNT_TITLE = "vote_count";

    private ComingSoonContainer comingSoonContainer;
    private Context context;
    private ComingSoonItemAdapter comingSoonItemAdapter;
    private InputStream inputStream;
    private String jsonString;
    private JSONObject jsonObject;

    private SearchMovieContainer searchMovieContainer;

    private ProgressDialog progressDialog;
    private ArrayList<Bitmap> images;

    public ComingSoon(Context context, ComingSoonItemAdapter comingSoonItemAdapter, ComingSoonContainer comingSoonContainer) {
        this.progressDialog = ProgressDialog.show(context, "Loading", "Loading. Please wait...");
        this.context = context;
        this.comingSoonContainer = comingSoonContainer;
        this.comingSoonItemAdapter = comingSoonItemAdapter;
        images = new ArrayList<Bitmap>();
    }

    @Override
    protected void onPostExecute(SearchMovieContainer searchMovieContainer) {
        for(int i = 0; i < searchMovieContainer.getSearchMovieElementArrayList().size(); i++) {
            ComingSoonItem comingSoonItem = new ComingSoonItem();
            comingSoonItem.setTitle(searchMovieContainer.getSearchMovieElementArrayList().get(i).getTitle());
            comingSoonItem.setReleaseDate(searchMovieContainer.getSearchMovieElementArrayList().get(i).getRelease_date());
            comingSoonItem.setRating(searchMovieContainer.getSearchMovieElementArrayList().get(i).getVote_average());
            comingSoonItem.setPosterLink(searchMovieContainer.getSearchMovieElementArrayList().get(i).getPoster_path());
            comingSoonItem.setVotes(searchMovieContainer.getSearchMovieElementArrayList().get(i).getVote_count());

            comingSoonContainer.getSearchResultsItemArrayList().add(comingSoonItem);
        }
        this.progressDialog.hide();
        comingSoonItemAdapter.notifyDataSetChanged();

        for(int i = 0; i < searchMovieContainer.getSearchMovieElementArrayList().size(); i++) {
            PosterLoader posterLoader = new PosterLoader(searchMovieContainer, images, comingSoonItemAdapter, comingSoonContainer, i);
            posterLoader.execute();
        }
    }

    @Override
    protected SearchMovieContainer doInBackground(String... params) {
        SearchMovieContainer searchMovieContainer = null;
        String url = BASE_URL + "?" + API_KEY_TITLE + "=" + API_KEY;
        jsonObject = getJSONObject(url);
        searchMovieContainer = parseJSONObject(jsonObject);
        return searchMovieContainer;
    }

    public JSONObject getJSONObject(String url) {
        // Making HTTP request
        try {
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        } catch(ClientProtocolException exception) {
            exception.printStackTrace();
        } catch(IOException exception) {
            exception.printStackTrace();
        } catch(IllegalStateException exception) {
            exception.printStackTrace();
        }

        // Convert input stream to string
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            inputStream.close();
            jsonString = stringBuilder.toString();
        } catch(IOException exception) {
            exception.printStackTrace();
        }

        // Try parse json string into json object
        try {
            jsonObject = new JSONObject(jsonString);
        } catch(JSONException exception) {
            exception.printStackTrace();
        }
        return jsonObject;
    }

    // Parse json into ComingSoonContainer
    public SearchMovieContainer parseJSONObject(JSONObject jsonObject) {
        searchMovieContainer = new SearchMovieContainer();

        try {
            int pages = jsonObject.getInt(API_TOTAL_PAGES_TITLE);

            for(int i = 1; i <= pages; i++) {
                String url = BASE_URL + "?" + API_KEY_TITLE + "=" + API_KEY + "&" + API_PAGE_TITLE + "=" + Integer.toString(i);
                jsonObject = getJSONObject(url);
                parseJSONObject(searchMovieContainer, jsonObject);
            }


        } catch(JSONException exception) {
            exception.printStackTrace();
        }
        return searchMovieContainer;
    }

    // Parse json into ComingSoonContainer
    public void parseJSONObject(SearchMovieContainer searchMovieContainer, JSONObject jsonObject) {
        // Try to parse
        try {
            // Get the json array from json object
            JSONArray jsonArray = jsonObject.getJSONArray(API_RESULTS_TITLE);
            JSONObject myJSONObject = null;

            for(int i = 0; i < jsonArray.length(); i++) {
                SearchMovieElement searchMovieElement = new SearchMovieElement();
                myJSONObject = jsonArray.getJSONObject(i);
                // Set json fields to SearchMovieElement object

                searchMovieElement.setAdult(myJSONObject.getString(API_RESULTS_ADULT_TITLE));
                searchMovieElement.setBackdrop_path(myJSONObject.getString(API_RESULTS_BACKDROP_PATH_TITLE));
                searchMovieElement.setId(myJSONObject.getString(API_RESULTS_ID_TITLE));
                searchMovieElement.setOriginal_title(myJSONObject.getString(API_RESULTS_ORIGINAL_TITLE_TITLE));
                searchMovieElement.setRelease_date(myJSONObject.getString(API_RESULTS_RELEASE_DATE_TITLE));
                searchMovieElement.setPoster_path(myJSONObject.getString(API_RESULTS_POSTER_PATH_TITLE));
                searchMovieElement.setPopularity(myJSONObject.getString(API_RESULTS_POPULARITY_TITLE));
                searchMovieElement.setTitle(myJSONObject.getString(API_RESULTS_TITLE_TITLE));
                searchMovieElement.setVote_average(myJSONObject.getString(API_RESULTS_VOTE_AVERAGE_TITLE));
                searchMovieElement.setVote_count(myJSONObject.getString(API_RESULTS_VOTE_COUNT_TITLE));

                searchMovieContainer.getSearchMovieElementArrayList().add(searchMovieElement);
            }
        } catch(JSONException exception) {
            exception.printStackTrace();
        }
    }
}
