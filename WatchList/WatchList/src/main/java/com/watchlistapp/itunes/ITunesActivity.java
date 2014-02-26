package com.watchlistapp.itunes;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.widget.ListView;

import com.watchlistapp.R;

/**
 * Created by VEINHORN on 23/02/14.
 */
public class ITunesActivity extends ActionBarActivity {
    private ListView listView;
    private ITunesItemsContainer iTunesItemsContainer;
    private ITunesItemsAdapter iTunesItemsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itunes);
        String movieTitle = getIntent().getStringExtra("movieTitle");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_background_color)));
        actionBar.setTitle(Html.fromHtml("<b><font color=\"#424242\">" + movieTitle + " on iTunes" + "</font></b>"));

        listView = (ListView)findViewById(R.id.activity_itunes_list);

        iTunesItemsContainer = new ITunesItemsContainer();

        iTunesItemsAdapter = new ITunesItemsAdapter(this, iTunesItemsContainer);
        listView.setAdapter(iTunesItemsAdapter);

        ITunesLoader iTunesLoader = new ITunesLoader(this, movieTitle, iTunesItemsContainer, iTunesItemsAdapter);
        iTunesLoader.loadDataFromITunes();
    }
}
