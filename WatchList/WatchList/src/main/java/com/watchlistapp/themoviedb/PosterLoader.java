package com.watchlistapp.themoviedb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.watchlistapp.searchresults.SearchResultsContainer;
import com.watchlistapp.searchresults.SearchResultsItemAdapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by VEINHORN on 03/12/13.
 */
public class PosterLoader extends AsyncTask<String, Integer, ArrayList<Bitmap>> {

    String url;
    ArrayList<Bitmap> images;
    SearchMovieContainer searchMovieContainer;
    SearchResultsItemAdapter searchResultsItemAdapter;
    SearchResultsContainer searchResultsContainer;
    int position;

    public PosterLoader(SearchMovieContainer searchMovieContainer, ArrayList<Bitmap> images, SearchResultsItemAdapter searchResultsItemAdapter, SearchResultsContainer searchResultsContainer, int position) {
        this.searchMovieContainer = searchMovieContainer;
        this.images = images;
        this.searchResultsItemAdapter = searchResultsItemAdapter;
        this.searchResultsContainer = searchResultsContainer;
        this.position = position;
    }


    @Override
    protected void onPostExecute(ArrayList<Bitmap> images) {
        /*
        for(int i = 0; i < searchMovieContainer.getSearchMovieElementArrayList().size(); i++) {
            searchResultsContainer.getSearchResultsItemArrayList().get(i).setPoster(images.get(i));
            searchResultsItemAdapter.notifyDataSetChanged();
        }*/
        searchResultsItemAdapter.notifyDataSetChanged();
    }


    @Override
    protected ArrayList<Bitmap> doInBackground(String... params) {
        URL url = null;
        Bitmap bmp = null;
            try {
                url = new URL("http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w185" + searchMovieContainer.getSearchMovieElementArrayList().get(position).getPoster_path());
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                searchResultsContainer.getSearchResultsItemArrayList().get(position).setPoster(bmp);
            } catch(MalformedURLException exception) {
                exception.printStackTrace();
            } catch(IOException exception) {
                exception.printStackTrace();
            }
            images.add(bmp);
        return images;
    }
}
