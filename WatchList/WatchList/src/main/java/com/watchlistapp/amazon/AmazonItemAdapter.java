package com.watchlistapp.amazon;

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
 * Created by VEINHORN on 22/02/14.
 */
public class AmazonItemAdapter extends BaseAdapter {
    private static class ViewHolder {
        public TextView title;
        public TextView price;
        private ImageView poster;
    }

    private AmazonItemsContainer amazonItemsContainer;
    private Context context;
    private LayoutInflater layoutInflater;

    public AmazonItemAdapter(Context context, AmazonItemsContainer amazonItemsContainer) {
        this.context = context;
        this.amazonItemsContainer = amazonItemsContainer;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return amazonItemsContainer.getAmazonItemArrayList().size();
    }

    @Override
    public Object getItem(int position) {
        return amazonItemsContainer.getAmazonItemArrayList().get(position);
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
            viewHolder.price = (TextView)convertView.findViewById(R.id.amazon_list_activity_price);
            viewHolder.poster = (ImageView)convertView.findViewById(R.id.amazon_list_activity_poster);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.title.setText(amazonItemsContainer.getAmazonItemArrayList().get(position).getTitle());
        viewHolder.price.setText(amazonItemsContainer.getAmazonItemArrayList().get(position).getPrice());
        viewHolder.poster.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in_amazon));
        Picasso.with(context).load(amazonItemsContainer.getAmazonItemArrayList().get(position).getPosterUrl()).into(viewHolder.poster);
        return convertView;
    }
}
