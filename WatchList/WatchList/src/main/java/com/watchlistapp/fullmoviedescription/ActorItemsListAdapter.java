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
 * Created by VEINHORN on 07/01/14.
 */
public class ActorItemsListAdapter extends BaseAdapter {

    private static class ViewHolder {
        public ImageView actorAvatar;
        public TextView name;
        public TextView character;
    }

    private Context context;
    private ActorItemsContainer actorItemsContainer;
    private LayoutInflater layoutInflater;

    public ActorItemsListAdapter(Context context, ActorItemsContainer actorItemsContainer) {
        this.context = context;
        this.actorItemsContainer = actorItemsContainer;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return actorItemsContainer.getActorItemArrayList().size();
    }

    @Override
    public Object getItem(int position) {
        return actorItemsContainer.getActorItemArrayList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.actor_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.actorAvatar =  (ImageView)convertView.findViewById(R.id.actorAvatar);
            viewHolder.name = (TextView)convertView.findViewById(R.id.actorName);
            viewHolder.character = (TextView)convertView.findViewById(R.id.actorCharacter);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        new ActorAvatarLoader(context, viewHolder.actorAvatar, actorItemsContainer.getActorItemArrayList().get(position).getProfile_path(), DisplayUtil.getActorAvatarSize(context)).loadPoster();
        viewHolder.name.setText(actorItemsContainer.getActorItemArrayList().get(position).getName());
        viewHolder.character.setText(actorItemsContainer.getActorItemArrayList().get(position).getCharacter());

        return convertView;
    }
}
