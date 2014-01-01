package com.watchlistapp.watchlistserver;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by VEINHORN on 31/12/13.
 */
public class AddMovieToListHandler extends AsyncTask<String, Integer, Integer> {

    private final static String BASE_URL = "http://watchlist-app-server.herokuapp.com/list/";

    private final static String API_ADD_MOVIE_TITLE = "addMovie";
    private final static String API_MOVIE_ID_TITLE = "movieId";
    private final static String API_USER_ID_TITLE = "userId";
    private final static String API_LIST_TITLE_TITLE = "listTitle";

    String movieId;
    String userId;
    String listTitle;

    String testString;

    public AddMovieToListHandler(String movieId, String userId, String listTitle) {
        this.movieId = movieId;
        this.userId = userId;
        this.listTitle = listTitle;
    }

    @Override
    protected Integer doInBackground(String... params) {
        String url = BASE_URL + API_ADD_MOVIE_TITLE + "?" + API_MOVIE_ID_TITLE + "=" +
                movieId + "&" + API_USER_ID_TITLE + "=" + userId + "&" + API_LIST_TITLE_TITLE + "=" + listTitle;
        url = url.replaceAll(" ", "%20");
        testString = url;
        doRequest(url);

        return new Integer(123);
    }

    private void doRequest(String url) {
        // Making HTTP request
        try {
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
        } catch(ClientProtocolException exception) {
            exception.printStackTrace();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }
}
