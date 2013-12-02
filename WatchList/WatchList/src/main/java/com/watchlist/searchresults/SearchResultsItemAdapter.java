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
        layoutInflater = LayoutInflater.from(context);
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
            viewHolder.poster = (ImageView)convertView.findViewById(R.id.search_results_poster);
            viewHolder.title = (TextView)convertView.findViewById(R.id.search_results_title);
            viewHolder.rating = (TextView)convertView.findViewById(R.id.search_results_rating);
            viewHolder.releaseDate = (TextView)convertView.findViewById(R.id.search_results_release_date);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.title.setText(searchResultsContainer.getSearchResultsItemArrayList().get(position).getTitle());
        viewHolder.rating.setText(searchResultsContainer.getSearchResultsItemArrayList().get(position).getRating());
        viewHolder.releaseDate.setText(searchResultsContainer.getSearchResultsItemArrayList().get(position).getReleaseDate());
        viewHolder.poster.setImageBitmap(searchResultsContainer.getSearchResultsItemArrayList().get(position).getPoster());
        return convertView;
    }
}
