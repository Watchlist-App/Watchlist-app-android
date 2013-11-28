package com.watchlist.navigationdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.watchlist.R;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 26/11/13.
 */

public class NavigationDrawerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PlaylistItem> playlistItemArrayList;
    private LayoutInflater layoutInflater;

    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView count;
    }

    public NavigationDrawerAdapter(Context context, ArrayList<PlaylistItem> playlists) {
        this.context = context;
        this.playlistItemArrayList = playlists;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return playlistItemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return playlistItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.drawer_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView)convertView.findViewById(R.id.nav_drawer_playlist_icon);
            viewHolder.title = (TextView)convertView.findViewById(R.id.nav_drawer_playlist_title);
            viewHolder.count = (TextView)convertView.findViewById(R.id.nav_drawer_playlist_count);

            viewHolder.icon.setImageResource(playlistItemArrayList.get(position).getIcon());
            viewHolder.title.setText(playlistItemArrayList.get(position).getTitle());
            viewHolder.count.setText(Integer.toString(playlistItemArrayList.get(position).getMovies()));

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }
}
