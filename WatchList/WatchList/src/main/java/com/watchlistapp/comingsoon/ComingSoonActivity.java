package com.watchlistapp.comingsoon;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.watchlistapp.R;
import com.watchlistapp.searchresults.SearchResultsActivity;
import com.watchlistapp.themoviedb.ComingSoon;

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

        comingSoonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(ComingSoonActivity.this, comingSoonContainer.getSearchResultsItemArrayList().get(position).getMovieId(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        comingSoonContainer = new ComingSoonContainer();
        comingSoonItemAdapter = new ComingSoonItemAdapter(ComingSoonActivity.this, comingSoonContainer);
        comingSoonListView.setAdapter(comingSoonItemAdapter);

        ComingSoon comingSoon = new ComingSoon(ComingSoonActivity.this, comingSoonItemAdapter, comingSoonContainer);
        comingSoon.execute();
    }
}
