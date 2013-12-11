package com.watchlist.comingsoon;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.watchlist.R;
import com.watchlist.themoviedb.ComingSoon;

/**
 * Created by VEINHORN on 11/12/13.
 */

public class ComingSoonActivity extends ActionBarActivity {

    private ListView comingSoonListView;
    private ComingSoonItemAdapter comingSoonItemAdapter;
    private ComingSoonContainer comingSoonContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);

        comingSoonListView = (ListView)findViewById(R.id.coming_soon_listview);
        comingSoonContainer = new ComingSoonContainer();
        comingSoonItemAdapter = new ComingSoonItemAdapter(ComingSoonActivity.this, comingSoonContainer);
        comingSoonListView.setAdapter(comingSoonItemAdapter);

        ComingSoon comingSoon = new ComingSoon(ComingSoonActivity.this, comingSoonItemAdapter, comingSoonContainer);
        comingSoon.execute();
    }
}
