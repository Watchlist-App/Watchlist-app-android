package com.watchlistapp.amazon;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.widget.ListView;

import com.watchlistapp.R;

/**
 * Created by VEINHORN on 22/02/14.
 */
public class AmazonActivity extends ActionBarActivity {
    private ListView listView;
    private AmazonItemsContainer amazonItemsContainer;
    private AmazonItemAdapter amazonItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_background_color)));
        actionBar.setTitle(Html.fromHtml("<b><font color=\"#424242\">" + getIntent().getStringExtra("movieTitle") + " on Amazon" + "</font></b>"));

        listView = (ListView)findViewById(R.id.activity_amazon_list);

        amazonItemsContainer = new AmazonItemsContainer();

        amazonItemAdapter = new AmazonItemAdapter(this, amazonItemsContainer);
        listView.setAdapter(amazonItemAdapter);

        String movieTitle = getIntent().getStringExtra("movieTitle");
        AmazonLoader amazonLoader = new AmazonLoader(this, movieTitle, amazonItemsContainer, amazonItemAdapter);
        amazonLoader.loadDataFromAmazon();
    }
}
