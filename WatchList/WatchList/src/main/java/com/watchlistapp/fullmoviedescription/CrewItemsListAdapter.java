package com.watchlistapp.fullmoviedescription;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.watchlistapp.R;
import com.watchlistapp.utils.DisplayUtil;

/**
 * Created by VEINHORN on 08/01/14.
 */
public class CrewItemsListAdapter extends BaseAdapter {

    private static class ViewHolder {
        public ImageView crewAvatar;
        public TextView name;
        public TextView job;
    }

    private Context context;
    private CrewItemsContainer crewItemsContainer;
    private LayoutInflater layoutInflater;

    public CrewItemsListAdapter(Context context, CrewItemsContainer crewItemsContainer) {
        this.context = context;
        this.crewItemsContainer = crewItemsContainer;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return crewItemsContainer.getCrewItemArrayList().size();
    }

    @Override
    public Object getItem(int position) {
        return crewItemsContainer.getCrewItemArrayList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.crew_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.crewAvatar = (ImageView)convertView.findViewById(R.id.crewAvatar);
            viewHolder.name = (TextView)convertView.findViewById(R.id.crewName);
            viewHolder.job = (TextView)convertView.findViewById(R.id.crewJob);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        new NewCrewAvatarLoader(context, viewHolder.crewAvatar, crewItemsContainer.getCrewItemArrayList().get(position).getProfilePath(), DisplayUtil.getCrewAvatarSize(context)).loadPoster();
        viewHolder.name.setText(crewItemsContainer.getCrewItemArrayList().get(position).getName());
        viewHolder.job.setText(crewItemsContainer.getCrewItemArrayList().get(position).getJob());

        return convertView;
    }
}
