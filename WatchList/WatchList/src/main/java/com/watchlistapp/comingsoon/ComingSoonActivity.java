package com.watchlistapp.comingsoon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.watchlistapp.R;
import com.watchlistapp.fullmoviedescription.FullMovieDescriptionActivity;

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
                Intent intent = new Intent(ComingSoonActivity.this, FullMovieDescriptionActivity.class);
                intent.putExtra("movieId", comingSoonContainer.getSearchResultsItemArrayList().get(position).getMovieId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
            }
        });

        comingSoonContainer = new ComingSoonContainer();
        comingSoonItemAdapter = new ComingSoonItemAdapter(ComingSoonActivity.this, comingSoonContainer, ComingSoonActivity.this, comingSoonListView);
        comingSoonListView.setAdapter(comingSoonItemAdapter);

        ComingSoon comingSoon = new ComingSoon(ComingSoonActivity.this, comingSoonItemAdapter, comingSoonContainer);
        comingSoon.execute();
    }
}
