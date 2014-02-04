package com.watchlistapp.fullmoviedescription;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.watchlistapp.R;
import com.watchlistapp.photoviewlib.PhotoViewAttacher;

/**
 * Created by VEINHORN on 02/01/14.
 */
public class FullPosterViewActivity extends ActionBarActivity {
    private ImageView posterImageView;
    private PhotoViewAttacher photoViewAttacher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_poster_view);

        getSupportActionBar().hide();

        posterImageView = (ImageView)findViewById(R.id.activity_full_poster_view_photo_view);
        String posterPath = getIntent().getStringExtra("posterUrl");
        FullPosterLoader fullPosterLoader = new FullPosterLoader(posterImageView, posterPath, FullPosterLoader.ORIGINAL, photoViewAttacher, FullPosterViewActivity.this);
        fullPosterLoader.execute();
    }
}
