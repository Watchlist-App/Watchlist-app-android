package com.watchlistapp.fullmoviedescription;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by VEINHORN on 26/12/13.
 */
public class FullDescriptionLoader extends AsyncTask<String, Integer, MovieDescription> {

    private final static String BASE_URL = "http://api.themoviedb.org/3/movie/";

    private final static String API_KEY_TITLE = "api_key";
    private final static String API_KEY = "2b7854ef68a3c274a0f804c031285c46";

    private final static String API_BACKDROP_PATH_TITLE = "backdrop_path";
    private final static String API_BUDGET_TITLE = "budget";
    private final static String API_GENRES_TITLE = "genres";
    private final static String API_GENRES_NAME_TITLE = "name";
    private final static String API_HOMEPAGE_TITLE = "homepage";
    private final static String API_OVERVIEW_TITLE = "overview";
    private final static String API_POSTER_PATH_TITLE = "poster_path";
    private final static String API_RELEASE_DATE_TITLE = "release_date";
    private final static String API_RUNTIME_TITLE = "release_date";
    private final static String API_STATUS_TITLE = "status";
    private final static String API_TAGLINE_TITLE = "tagline";
    private final static String API_TITLE_TITLE = "title";
    private final static String API_VOTE_AVERAGE_TITLE = "vote_average";
    private final static String API_VOTE_COUNT_TITLE = "vote_count";
    private final static String API_MOVIE_ID_TITLE = "id";

    private Context context;
    private String movieId;

    // Views
    TextView movieTitleTextView;
    ImageView posterImageView;
    TextView movieOverviewTextView;

    public FullDescriptionLoader(Context context, String movieId, TextView movieTitleTextView, ImageView posterImageView, TextView movieOverviewTextView) {
        this.context = context;
        this.movieId = movieId;

        this.movieTitleTextView = movieTitleTextView;
        this.posterImageView = posterImageView;
        this.movieOverviewTextView = movieOverviewTextView;
    }

    @Override
    protected void onPostExecute(MovieDescription movieDescription) {
        movieTitleTextView.setText(movieDescription.getTitle());
        movieOverviewTextView.setText(movieDescription.getOverview());

        PosterLoader posterLoader = new PosterLoader(posterImageView, movieDescription.getPosterPath(), PosterLoader.DOUBLE_BIG);
        posterLoader.execute();
    }

    @Override
    protected MovieDescription doInBackground(String... params) {
        MovieDescription movieDescription = null;
        String url = BASE_URL + movieId + "?" + API_KEY_TITLE + "=" + API_KEY;
        JSONObject jsonObject = getJSONObject(url);

        movieDescription = parseJSONObject(jsonObject);

        return movieDescription;
    }

    private MovieDescription parseJSONObject(JSONObject jsonObject) {
        MovieDescription movieDescription = new MovieDescription();

        try {
            movieDescription.setBackdropPath(jsonObject.getString(API_BACKDROP_PATH_TITLE));
            movieDescription.setBudget(jsonObject.getString(API_BUDGET_TITLE));

            // Get genres
            JSONArray genresJsonArray = jsonObject.getJSONArray(API_GENRES_TITLE);
            ArrayList<String> genreNames = new ArrayList<String>();
            for(int i = 0; i < genresJsonArray.length(); i++) {
                JSONObject genreJsonObject = null;
                try {
                    genreJsonObject = genresJsonArray.getJSONObject(i);
                    genreNames.add(genreJsonObject.getString(API_GENRES_NAME_TITLE));
                } catch(JSONException exception) {
                    exception.printStackTrace();
                }
            }
            movieDescription.setGenres(genreNames);

            movieDescription.setHomepage(jsonObject.getString(API_HOMEPAGE_TITLE));
            movieDescription.setOverview(jsonObject.getString(API_OVERVIEW_TITLE));
            movieDescription.setPosterPath(jsonObject.getString(API_POSTER_PATH_TITLE));
            movieDescription.setReleaseDate(jsonObject.getString(API_RELEASE_DATE_TITLE));
            movieDescription.setRuntime(jsonObject.getString(API_RUNTIME_TITLE));
            movieDescription.setStatus(jsonObject.getString(API_STATUS_TITLE));
            movieDescription.setTagline(jsonObject.getString(API_TAGLINE_TITLE));
            movieDescription.setTitle(jsonObject.getString(API_TITLE_TITLE));
            movieDescription.setVoteAverage(jsonObject.getString(API_VOTE_AVERAGE_TITLE));
            movieDescription.setVoteCount(jsonObject.getString(API_VOTE_COUNT_TITLE));
            movieDescription.setMovieId(jsonObject.getString(API_MOVIE_ID_TITLE));
        } catch(JSONException exception) {
            exception.printStackTrace();
        }
        return movieDescription;
    }

    private JSONObject getJSONObject(String url) {
        InputStream inputStream = getInputStream(url);
        String jsonString = convertInputStreamToString(inputStream);

        JSONObject jsonObject = null;
        // Try to parse json string
        try {
            jsonObject = new JSONObject(jsonString);
        }catch(JSONException exception) {
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
