package com.watchlistapp.navigationdrawer;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.watchlistapp.R;
import com.watchlistapp.authorization.GAvatar;

/**
 * Created by VEINHORN on 11/12/13.
 */
public class NavigationDrawerItemAdapter extends BaseAdapter {

    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
    }

    private Context context;
    private NavigationDrawerItemContainer navigationDrawerItemContainer;
    private LayoutInflater layoutInflater;

    public NavigationDrawerItemAdapter() {

    }

    public NavigationDrawerItemAdapter(Context context, NavigationDrawerItemContainer navigationDrawerItemContainer) {
        this.navigationDrawerItemContainer = navigationDrawerItemContainer;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return navigationDrawerItemContainer.getNavigationDrawerItemArrayList().size();
    }

    @Override
    public Object getItem(int position) {
        return navigationDrawerItemContainer.getNavigationDrawerItemArrayList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.navigation_drawer_item, null);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView)convertView.findViewById(R.id.navigation_drawer_item_icon);
            viewHolder.title = (TextView)convertView.findViewById(R.id.navigation_drawer_item_title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        if(navigationDrawerItemContainer.getNavigationDrawerItemArrayList().get(position).getIconId() != 0) {
            viewHolder.icon.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), navigationDrawerItemContainer.getNavigationDrawerItemArrayList().get(position).getIconId()));
        } else {
            new GAvatar(context, viewHolder.icon, GAvatar.SMALL).loadGAvatar();
        }

        viewHolder.title.setText(navigationDrawerItemContainer.getNavigationDrawerItemArrayList().get(position).getTitle());

        return convertView;
    }
}
