package com.watchlistapp.themoviedb;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.watchlistapp.R;
import com.watchlistapp.apikeys.DeveloperKeys;
import com.watchlistapp.searchresults.SearchResultsContainer;
import com.watchlistapp.searchresults.SearchResultsItem;
import com.watchlistapp.searchresults.SearchResultsItemAdapter;
import com.watchlistapp.utils.RequestsUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by VEINHORN on 02/12/13.
 */
public class SearchMovies extends AsyncTask<String, Integer, SearchMovieContainer> {
    private final static String BASE_URL = "http://api.themoviedb.org/3/search/movie";
    // Required Parameters
    private final static String API_QUERY_TITLE = "query";
    public final static String API_KEY_TITLE = "api_key";
    // Optional Parameters
    private final static String API_PAGE_TITLE = "page";
    private final static String API_LANGUAGE_TITLE = "language";
    private final static String API_INCLUDE_ADULT_TITLE = "include_adult";
    private final static String API_YEAR_TITLE = "year";
    private final static String API_PRIMARY_RELEASE_YEAR_TITLE = "primary_release_year";
    private final static String API_SEARCH_TYPE_TITLE = "search_type";
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

    private Context context;
    private String searchQueryString; // string that stores info that user searches

    //
    private JSONObject jsonObject;
    //private ProgressDialog progressDialog;
    private String jsonString;
    private InputStream inputStream;
    private SearchMovieContainer searchMovieContainer;

    private SearchResultsItemAdapter searchResultsItemAdapter;
    private SearchResultsContainer searchResultsContainer;

    private ArrayList<Bitmap> images;

    private Activity activity;

    public SearchMovies(Context context, String searchQueryString, SearchResultsItemAdapter searchResultsItemAdapter, SearchResultsContainer searchResultsContainer, Activity activity) {
        this.searchResultsItemAdapter = searchResultsItemAdapter;
        this.searchResultsContainer = searchResultsContainer;
        this.context = context;
        this.searchQueryString = searchQueryString;
        //this.progressDialog = ProgressDialog.show(context, "Search", "Searching. Please wait...");
        images = new ArrayList<Bitmap>();

        this.activity = activity;
    }

    @Override
    protected void onPostExecute(SearchMovieContainer searchMovieContainer) {

        if(searchMovieContainer.getSearchMovieElementArrayList().isEmpty()) {
            activity.setContentView(R.layout.activity_no_movies);
            return;
        }

        for(int i = 0; i < searchMovieContainer.getSearchMovieElementArrayList().size(); i++) {
            SearchResultsItem searchResultsItem = new SearchResultsItem();
            searchResultsItem.setTitle(searchMovieContainer.getSearchMovieElementArrayList().get(i).getTitle());
            searchResultsItem.setReleaseDate(searchMovieContainer.getSearchMovieElementArrayList().get(i).getRelease_date());
            searchResultsItem.setRating(searchMovieContainer.getSearchMovieElementArrayList().get(i).getVote_average());
            searchResultsItem.setPosterLink(searchMovieContainer.getSearchMovieElementArrayList().get(i).getPoster_path());
            searchResultsItem.setVotes(searchMovieContainer.getSearchMovieElementArrayList().get(i).getVote_count());
            searchResultsItem.setMovieId(searchMovieContainer.getSearchMovieElementArrayList().get(i).getId());
            searchResultsContainer.getSearchResultsItemArrayList().add(searchResultsItem);
        }
        searchResultsItemAdapter.notifyDataSetChanged();

        for(int i = 0; i < searchMovieContainer.getSearchMovieElementArrayList().size(); i++) {
            PosterLoader posterLoader = new PosterLoader(searchMovieContainer, images, searchResultsItemAdapter, searchResultsContainer, i);
            posterLoader.execute();
        }

        //loadImages();
        //progressDialog.hide();
    }

    @Override
    protected SearchMovieContainer doInBackground(String... params) {
        SearchMovieContainer searchMovieContainer = null;
        String url = BASE_URL + "?" + API_KEY_TITLE + "=" + DeveloperKeys.THE_MOVIE_DB_DEVELOPER_KEY + "&" + API_QUERY_TITLE + "=" + searchQueryString;
        url = url.replaceAll(" ", "%20");
        jsonObject = RequestsUtil.getJSONObject(url);
        searchMovieContainer = parseJSONObject(jsonObject);
        //loadImages();

        return searchMovieContainer;
    }

    // Parse json into SearchMovieContainer
    public SearchMovieContainer parseJSONObject(JSONObject jsonObject) {
        searchMovieContainer = new SearchMovieContainer();

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

        return searchMovieContainer;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getSearchQueryString() {
        return searchQueryString;
    }

    public void setSearchQueryString(String searchQueryString) {
        this.searchQueryString = searchQueryString;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public SearchMovieContainer getSearchMovieContainer() {
        return searchMovieContainer;
    }

    public void setSearchMovieContainer(SearchMovieContainer searchMovieContainer) {
        this.searchMovieContainer = searchMovieContainer;
    }
}
