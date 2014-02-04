package com.watchlistapp.comingsoon;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.watchlistapp.themoviedb.SearchMovieContainer;
import com.watchlistapp.themoviedb.SearchMovieElement;
import com.watchlistapp.utils.DeveloperKeys;
import com.watchlistapp.utils.RequestsUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by VEINHORN on 11/12/13.
 */

public class ComingSoon extends AsyncTask<String, Integer, SearchMovieContainer> {
    private final static String BASE_URL = "http://api.themoviedb.org/3/movie/upcoming";
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
        this.context = context;
        this.comingSoonContainer = comingSoonContainer;
        this.comingSoonItemAdapter = comingSoonItemAdapter;
        images = new ArrayList<Bitmap>();

        this.progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        this.progressDialog = ProgressDialog.show(context, "Loading", "Loading. Please wait...");
    }

    @Override
    protected void onPostExecute(SearchMovieContainer searchMovieContainer) {
        this.progressDialog.hide();

        for(int i = 0; i < searchMovieContainer.getSearchMovieElementArrayList().size(); i++) {
            ComingSoonItem comingSoonItem = new ComingSoonItem();
            comingSoonItem.setTitle(searchMovieContainer.getSearchMovieElementArrayList().get(i).getTitle());
            comingSoonItem.setReleaseDate(searchMovieContainer.getSearchMovieElementArrayList().get(i).getRelease_date());
            comingSoonItem.setRating(searchMovieContainer.getSearchMovieElementArrayList().get(i).getVote_average());
            comingSoonItem.setPosterLink(searchMovieContainer.getSearchMovieElementArrayList().get(i).getPoster_path());
            comingSoonItem.setVotes(searchMovieContainer.getSearchMovieElementArrayList().get(i).getVote_count());
            comingSoonItem.setMovieId(searchMovieContainer.getSearchMovieElementArrayList().get(i).getId());

            comingSoonContainer.getSearchResultsItemArrayList().add(comingSoonItem);
            comingSoonItemAdapter.notifyDataSetChanged();
        }
        this.progressDialog.hide();
    }

    @Override
    protected SearchMovieContainer doInBackground(String... params) {
        SearchMovieContainer searchMovieContainer = null;
        String url = BASE_URL + "?" + API_KEY_TITLE + "=" + DeveloperKeys.THE_MOVIE_DB_DEVELOPER_KEY;
        jsonObject = RequestsUtil.getJSONObject(url);
        searchMovieContainer = parseJSONObject(jsonObject);
        return searchMovieContainer;
    }

    // Parse json into ComingSoonContainer
    public SearchMovieContainer parseJSONObject(JSONObject jsonObject) {
        searchMovieContainer = new SearchMovieContainer();

        try {
            int pages = jsonObject.getInt(API_TOTAL_PAGES_TITLE);

            for(int i = 1; i <= pages; i++) {
                String url = BASE_URL + "?" + API_KEY_TITLE + "=" + DeveloperKeys.THE_MOVIE_DB_DEVELOPER_KEY + "&" + API_PAGE_TITLE + "=" + Integer.toString(i);
                jsonObject = RequestsUtil.getJSONObject(url);
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
