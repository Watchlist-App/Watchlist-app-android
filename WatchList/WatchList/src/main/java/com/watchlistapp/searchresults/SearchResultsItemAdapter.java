package com.watchlistapp.searchresults;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.watchlistapp.addmovietolistdialog.AddMovieToListDialogFragment;
import com.watchlistapp.R;
import com.watchlistapp.ratingbar.ColoredRatingBar;

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
        public ImageButton amazonButton;
        public ImageButton addToListButton;
    }

    private SearchResultsContainer searchResultsContainer;
    private Context context;
    private LayoutInflater layoutInflater;
    private Activity activity;

    public SearchResultsItemAdapter() {

    }

    public SearchResultsItemAdapter(Context context, SearchResultsContainer searchMovieContainer, Activity activity) {
        this.context = context;
        this.searchResultsContainer = searchMovieContainer;
        layoutInflater = LayoutInflater.from(context);
        this.activity = activity;
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

            viewHolder.amazonButton = (ImageButton)convertView.findViewById(R.id.search_results_activity_amazon_button);
            viewHolder.addToListButton = (ImageButton)convertView.findViewById(R.id.search_results_activity_add_to_list_button);
            //viewHolder.amazonButton.setOnClickListener(amazonButtonListener);
            viewHolder.addToListButton.setOnClickListener(addToListButtonListener);
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

    /*
    // Amazon button action listener
    private View.OnClickListener amazonButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            Intent intent = new Intent(context, AmazonListActivity.class);
            //final int position =
            //intent.putExtra("movietitle", );
            context.startActivity(intent);
        }
    };*/

    // Add to button listener
    private View.OnClickListener addToListButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            // DialogFragment.show() will take care of adding the fragment
            // in a transaction.  We also want to remove any currently showing
            // dialog, so make our own transaction and take care of that here.
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            Fragment prev = activity.getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);


            // Create and show the dialog
            DialogFragment newFragment = AddMovieToListDialogFragment.newInstance();
            newFragment.show(ft, "dialog");
        }
    };
}
