package com.watchlist.searchresults;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.watchlist.R;

/**
 * Created by VEINHORN on 02/12/13.
 */
public class SearchResultsItemAdapter extends BaseAdapter {

    private static class ViewHolder {
        public ImageView poster;
        public TextView title;
        public TextView releaseDate;
        public TextView rating;
    }

    private SearchResultsContainer searchResultsContainer;
    private Context context;
    private LayoutInflater layoutInflater;

    public SearchResultsItemAdapter() {

    }

    public SearchResultsItemAdapter(Context context, SearchResultsContainer searchMovieContainer) {
        this.context = context;
        this.searchResultsContainer = searchMovieContainer;
    }

    @Override
    public int getCount() {
        return searchResultsContainer.getSearchResultsItemArrayList().size();
    }

    @Override
    public Object getItem(int position) {
        return searchResultsContainer.getSearchResultsItemArrayList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.search_results_item, null);
            viewHolder = new ViewHolder();
            //viewHolder.
        }
    }
}
