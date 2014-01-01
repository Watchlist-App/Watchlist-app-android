package com.watchlistapp.fullmoviedescription;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.watchlistapp.R;

/**
 * Created by VEINHORN on 01/01/14.
 */
public class GenreItemAdapter extends BaseAdapter {

    private static class ViewHolder {
        TextView title;
    }

    private Context context;
    private GenreContainer genreContainer;
    private LayoutInflater layoutInflater;


    public GenreItemAdapter(Context context, GenreContainer genreContainer) {
        this.context = context;
        this.genreContainer = genreContainer;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return genreContainer.getGenresArrayList().size();
    }

    @Override
    public Object getItem(int position) {
        return genreContainer.getGenresArrayList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_full_movie_description_genre_grid_view_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.full_description_movie_genre);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.title.setText(genreContainer.getGenresArrayList().get(position).getTitle());

        return convertView;
    }
}
