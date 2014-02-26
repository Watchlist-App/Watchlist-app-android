package com.watchlistapp.itunes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.watchlistapp.R;

/**
 * Created by VEINHORN on 23/02/14.
 */
public class ITunesItemsAdapter extends BaseAdapter {
    private static class ViewHolder {
        public TextView title;
        public TextView price;
        public ImageView poster;
    }

    private ITunesItemsContainer iTunesItemsContainer;
    private Context context;
    private LayoutInflater layoutInflater;

    public ITunesItemsAdapter(Context context, ITunesItemsContainer iTunesItemsContainer) {
        this.context = context;
        this.iTunesItemsContainer = iTunesItemsContainer;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return iTunesItemsContainer.getiTunesItemArrayList().size();
    }

    @Override
    public Object getItem(int position) {
        return iTunesItemsContainer.getiTunesItemArrayList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.itunes_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.itunes_list_activity_title);
            viewHolder.price = (TextView)convertView.findViewById(R.id.itunes_list_activity_price);
            viewHolder.poster = (ImageView)convertView.findViewById(R.id.itunes_list_activity_poster);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.title.setText(iTunesItemsContainer.getiTunesItemArrayList().get(position).getTitle());
        viewHolder.price.setText(iTunesItemsContainer.getiTunesItemArrayList().get(position).getPrice());
        viewHolder.poster.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in_itunes));
        Picasso.with(context).load(iTunesItemsContainer.getiTunesItemArrayList().get(position).getPosterUrl()).into(viewHolder.poster);
        return convertView;
    }
}
