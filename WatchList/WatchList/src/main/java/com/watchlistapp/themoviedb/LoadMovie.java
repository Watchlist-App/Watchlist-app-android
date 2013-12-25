package com.watchlistapp.themoviedb;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.watchlistapp.database.WatchListDatabaseHandler;
import com.watchlistapp.searchresults.SearchResultsContainer;
import com.watchlistapp.searchresults.SearchResultsItem;
import com.watchlistapp.searchresults.SearchResultsItemAdapter;
import com.watchlistapp.watchlistserver.MovieContainer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by VEINHORN on 25/12/13.
 */
public class LoadMovie extends AsyncTask<String, Integer, SearchMovieContainer> {

    private final static String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private final static String API_KEY_TITLE = "api_key";
    private final static String API_KEY = "2b7854ef68a3c274a0f804c031285c46";

    private final static String API_TITLE_TITLE = "title";

    private Context context;
    private String listTitle;
    private SearchResultsItemAdapter searchResultsItemAdapter;
    private SearchResultsContainer searchResultsContainer;
    private ArrayList<Bitmap> images;

    public LoadMovie(Context context, String listTitle, SearchResultsItemAdapter searchResultsItemAdapter, SearchResultsContainer searchResultsContainer) {
        this.context = context;
        this.listTitle = listTitle;

        this.searchResultsItemAdapter = searchResultsItemAdapter;
        this.searchResultsContainer = searchResultsContainer;

        images = new ArrayList<Bitmap>();
    }

    @Override
    protected void onPostExecute(SearchMovieContainer searchMovieContainer) {
        for(int i = 0; i < searchMovieContainer.getSearchMovieElementArrayList().size(); i++) {
            SearchResultsItem searchResultsItem = new SearchResultsItem();
            searchResultsItem.setTitle(searchMovieContainer.getSearchMovieElementArrayList().get(i).getTitle());
            searchResultsItem.setReleaseDate(searchMovieContainer.getSearchMovieElementArrayList().get(i).getRelease_date());
            searchResultsItem.setRating(searchMovieContainer.getSearchMovieElementArrayList().get(i).getVote_average());
            searchResultsItem.setPosterLink(searchMovieContainer.getSearchMovieElementArrayList().get(i).getPoster_path());
            searchResultsItem.setVotes(searchMovieContainer.getSearchMovieElementArrayList().get(i).getVote_count());
            searchResultsContainer.getSearchResultsItemArrayList().add(searchResultsItem);
        }
        searchResultsItemAdapter.notifyDataSetChanged();

        for(int i = 0; i < searchMovieContainer.getSearchMovieElementArrayList().size(); i++) {
            PosterLoader posterLoader = new PosterLoader(searchMovieContainer, images, searchResultsItemAdapter, searchResultsContainer, i);
            posterLoader.execute();
        }
    }

    @Override
    protected SearchMovieContainer doInBackground(String... params) {
        SearchMovieContainer searchMovieContainer = new SearchMovieContainer();

        WatchListDatabaseHandler watchListDatabaseHandler = new WatchListDatabaseHandler(context);
        MovieContainer movieContainer = watchListDatabaseHandler.getMovieIdsByListTitle(listTitle);

        for(com.watchlistapp.watchlistserver.Movie myMovieId : movieContainer.getMovieArrayList()) {
            String url = BASE_URL + myMovieId.getId() + "?" + API_KEY_TITLE + "=" + API_KEY;
            JSONObject jsonObject = getJSONObject(url);
            Movie movie = parseJSONObject(jsonObject);

            SearchMovieElement searchMovieElement = new SearchMovieElement();
            searchMovieElement.setTitle(movie.getTitle());
            searchMovieElement.setVote_average(movie.getVoteAverage());
            searchMovieElement.setPoster_path(movie.getPosterPath());
            searchMovieElement.setVote_count(movie.getVoteCount());
            searchMovieElement.setRelease_date(movie.getReleaseDate());

            searchMovieContainer.getSearchMovieElementArrayList().add(searchMovieElement);
        }

        return searchMovieContainer;
    }

    private Movie parseJSONObject(JSONObject jsonObject) {
        Movie movie = new Movie();

        try {
            movie.setTitle(jsonObject.getString(API_TITLE_TITLE));
            movie.setReleaseDate(jsonObject.getString("release_date"));
            movie.setVoteAverage(jsonObject.getString("vote_average"));
            movie.setVoteCount(jsonObject.getString("vote_count"));
            movie.setPosterPath(jsonObject.getString("poster_path"));
        }catch(JSONException exception) {
            exception.printStackTrace();
        }

        return movie;
    }

    private JSONObject getJSONObject(String url) {
        InputStream inputStream = getInputStream(url);
        String jsonString = convertInputStreamToString(inputStream);

        JSONObject jsonObject = null;
        // Try to parse json string
        try {
            jsonObject = new JSONObject(jsonString);
        } catch(JSONException exception) {
            exception.printStackTrace();
        }
        return jsonObject;
    }

    // This method get InputStream from url
    private InputStream getInputStream(String url) {
        InputStream inputStream = null;

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
        return inputStream;
    }

    // This method converts InputStream to json string
    private String convertInputStreamToString(InputStream inputStream) {
        String jsonString = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            inputStream.close();
            jsonString = stringBuilder.toString();
        } catch(UnsupportedEncodingException exception) {
            exception.printStackTrace();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
        return jsonString;
    }
}
