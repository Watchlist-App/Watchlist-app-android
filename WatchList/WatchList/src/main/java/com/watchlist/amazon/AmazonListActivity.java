package com.watchlist.amazon;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.watchlist.R;

/**
 * Created by VEINHORN on 14/12/13.
 */
public class AmazonListActivity extends ActionBarActivity {

    private ListView amazonListView;
    private AmazonItemAdapter amazonItemAdapter;
    private AmazonContainer amazonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon_list);

        amazonListView = (ListView)findViewById(R.id.activity_amazonlist_listview);
        amazonContainer = new AmazonContainer();
        amazonItemAdapter = new AmazonItemAdapter(AmazonListActivity.this, amazonContainer);
        amazonListView.setAdapter(amazonItemAdapter);

        Amazon amazon = new Amazon(AmazonListActivity.this, amazonItemAdapter, amazonContainer);
        amazon.execute();
    }
}
