package com.watchlistapp.movielist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.watchlistapp.R;
import com.watchlistapp.themoviedb.Movie;
import com.watchlistapp.themoviedb.SearchMovieContainer;
import com.watchlistapp.themoviedb.SearchMovieElement;
import com.watchlistapp.utils.DeveloperKeys;
import com.watchlistapp.database.WatchListDatabaseHandler;
import com.watchlistapp.searchresults.SearchResultsContainer;
import com.watchlistapp.searchresults.SearchResultsItem;
import com.watchlistapp.searchresults.SearchResultsItemAdapter;
import com.watchlistapp.utils.RequestsUtil;
import com.watchlistapp.watchlistserver.MovieContainer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 25/12/13.
 */
public class LoadMovie extends AsyncTask<String, Integer, SearchMovieContainer> {

    private final static String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private final static String API_KEY_TITLE = "api_key";
    private final static String API_TITLE_TITLE = "title";

    private Activity activity;
    private Context context;
    private String listTitle;
    private SearchResultsItemAdapter searchResultsItemAdapter;
    private SearchResultsContainer searchResultsContainer;
    private ArrayList<Bitmap> images;
    private ProgressDialog progressDialog;

    public LoadMovie(Context context, String listTitle, SearchResultsItemAdapter searchResultsItemAdapter, SearchResultsContainer searchResultsContainer, Activity activity) {
        this.context = context;
        this.listTitle = listTitle;

        this.searchResultsItemAdapter = searchResultsItemAdapter;
        this.searchResultsContainer = searchResultsContainer;

        this.activity = activity;

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
    }

    @Override
    protected SearchMovieContainer doInBackground(String... params) {
        SearchMovieContainer searchMovieContainer = new SearchMovieContainer();

        WatchListDatabaseHandler watchListDatabaseHandler = new WatchListDatabaseHandler(context);
        MovieContainer movieContainer = watchListDatabaseHandler.getMovieIdsByListTitle(listTitle);

        for(com.watchlistapp.watchlistserver.Movie myMovieId : movieContainer.getMovieArrayList()) {
            String url = BASE_URL + myMovieId.getId() + "?" + API_KEY_TITLE + "=" + DeveloperKeys.THE_MOVIE_DB_DEVELOPER_KEY;
            JSONObject jsonObject = RequestsUtil.getJSONObject(url);
            Movie movie = parseJSONObject(jsonObject);

            SearchMovieElement searchMovieElement = new SearchMovieElement();
            searchMovieElement.setTitle(movie.getTitle());
            searchMovieElement.setVote_average(movie.getVoteAverage());
            searchMovieElement.setPoster_path(movie.getPosterPath());
            searchMovieElement.setVote_count(movie.getVoteCount());
            searchMovieElement.setRelease_date(movie.getReleaseDate());
            searchMovieElement.setId(myMovieId.getId());

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
}
