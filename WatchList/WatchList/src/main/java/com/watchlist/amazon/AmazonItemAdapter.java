package com.watchlist.amazon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.watchlist.R;

/**
 * Created by VEINHORN on 14/12/13.
 */
public class AmazonItemAdapter extends BaseAdapter {

    private AmazonContainer amazonContainer;
    private Context context;
    private LayoutInflater layoutInflater;

    private static class ViewHolder {
        public TextView title;
    }

    public AmazonItemAdapter() {

    }

    public AmazonItemAdapter(Context context, AmazonContainer amazonContainer) {
        this.context = context;
        this.amazonContainer = amazonContainer;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return amazonContainer.getAmazonElementArrayList().size();
    }

    @Override
    public Object getItem(int position) {
        return amazonContainer.getAmazonElementArrayList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.amazon_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.amazon_list_activity_title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.title.setText(amazonContainer.getAmazonElementArrayList().get(position).getDetailPageUrl());

        return convertView;
    }
}
