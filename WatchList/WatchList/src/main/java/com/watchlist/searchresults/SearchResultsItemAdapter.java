package com.watchlist.searchresults;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.watchlist.R;
import com.watchlist.ratingbar.ColoredRatingBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VEINHORN on 02/12/13.
 */
public class SearchResultsItemAdapter extends BaseAdapter {

    private static class ViewHolder {
        public ImageView poster;
        public TextView title;
        public TextView releaseDate;
        public TextView rating;
        public TextView votes;
        public ColoredRatingBar votesRatingBar;
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
            viewHolder.votes = (TextView)convertView.findViewById(R.id.search_results_votes);
            viewHolder.votesRatingBar = (ColoredRatingBar)convertView.findViewById(R.id.search_results_rating_bar);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        String title = searchResultsContainer.getSearchResultsItemArrayList().get(position).getTitle();
        String rating = searchResultsContainer.getSearchResultsItemArrayList().get(position).getRating() + "/" + "10";
        String votes = searchResultsContainer.getSearchResultsItemArrayList().get(position).getVotes() + " votes";
        // themoviedb has 10digits rating so here we convert it to 5digits
        Float ratingBarVote = Float.valueOf(searchResultsContainer.getSearchResultsItemArrayList().get(position).getRating()) / (float)2;
        String releaseDate = searchResultsContainer.getSearchResultsItemArrayList().get(position).getReleaseDate();
        // Sometimes on themoviedb date may be empty
        if(releaseDate.equals("")) {
            releaseDate = "Release date: Unknown";
        } else {
            releaseDate = "Release date: " + convertDate(releaseDate);
        }
        // Convert from one time format to another


        viewHolder.title.setText(title);
        viewHolder.rating.setText(rating);
        viewHolder.releaseDate.setText(releaseDate);
        viewHolder.votes.setText(votes);
        viewHolder.votesRatingBar.setRating(ratingBarVote);
        viewHolder.poster.setImageBitmap(searchResultsContainer.getSearchResultsItemArrayList().get(position).getPoster());
        return convertView;
    }

    private String convertDate(String inputString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(inputString);
        } catch(ParseException exception) {
            exception.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
        String postDate = postFormater.format(date);
        return postDate;
    }
}
