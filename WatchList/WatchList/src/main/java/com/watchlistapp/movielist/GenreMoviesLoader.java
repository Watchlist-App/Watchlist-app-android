package com.watchlistapp.movielist;

import android.app.Activity;
import android.content.Context;

import com.watchlistapp.utils.DeveloperKeys;
import com.watchlistapp.searchresults.SearchResultsContainer;
import com.watchlistapp.searchresults.SearchResultsItemAdapter;
import com.watchlistapp.themoviedb.SearchMovieContainer;
import com.watchlistapp.themoviedb.SearchMovies;
import com.watchlistapp.utils.RequestsUtil;

import org.json.JSONObject;

/**
 * Created by VEINHORN on 02/01/14.
 */
public class GenreMoviesLoader extends SearchMovies {

    public final static String BASE_URL = "http://api.themoviedb.org/3/genre/";
    public final static String API_MOVIES_TITLE = "movies";

    private String genreId;

    public GenreMoviesLoader(Context context, String searchQueryString, SearchResultsItemAdapter searchResultsItemAdapter, SearchResultsContainer searchResultsContainer, Activity activity, String genreId) {
        super(context, searchQueryString, searchResultsItemAdapter, searchResultsContainer, activity);
        this.genreId = genreId;
    }

    @Override
    protected SearchMovieContainer doInBackground(String... params) {
        SearchMovieContainer searchMovieContainer = null;
        String url = BASE_URL + genreId + "/" + API_MOVIES_TITLE + "?" + API_KEY_TITLE + "=" + DeveloperKeys.THE_MOVIE_DB_DEVELOPER_KEY;
        JSONObject jsonObject = RequestsUtil.getJSONObject(url);
        searchMovieContainer = parseJSONObject(jsonObject);
        return searchMovieContainer;
    }
}
