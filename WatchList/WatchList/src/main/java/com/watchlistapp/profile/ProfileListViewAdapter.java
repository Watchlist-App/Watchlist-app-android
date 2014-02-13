package com.watchlistapp.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.watchlistapp.R;

/**
 * Created by VEINHORN on 06/02/14.
 */
public class ProfileListViewAdapter extends BaseAdapter {
    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView moviesNumber;
    }
    private Context context;
    private LayoutInflater layoutInflater;
    private ProfileListViewContainer profileListViewContainer;

    public ProfileListViewAdapter() {

    }

    public ProfileListViewAdapter(Context context, ProfileListViewContainer profileListViewContainer) {
        this.context = context;
        this.profileListViewContainer = profileListViewContainer;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return profileListViewContainer.getProfileListViewItemArrayList().size();
    }

    @Override
    public Object getItem(int position) {
        return profileListViewContainer.getProfileListViewItemArrayList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_profile_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView)convertView.findViewById(R.id.profile_list_icon);
            viewHolder.title = (TextView)convertView.findViewById(R.id.profile_list_title);
            viewHolder.moviesNumber = (TextView)convertView.findViewById(R.id.profile_list_movies_numbers);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.title.setText(profileListViewContainer.getProfileListViewItemArrayList().get(position).getListTitle());
        viewHolder.moviesNumber.setText(profileListViewContainer.getProfileListViewItemArrayList().get(position).getMoviesNumber() + " " + context.getString(R.string.profile_activity_movies_number_title));
        viewHolder.icon.setImageResource(profileListViewContainer.getProfileListViewItemArrayList().get(position).getIcon());
        return convertView;
    }
}
