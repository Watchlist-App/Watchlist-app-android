package com.watchlistapp.addmovietolistdialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.watchlistapp.R;

/**
 * Created by VEINHORN on 29/12/13.
 */
public class AddMovieToListDialogListsItemAdapter extends BaseAdapter {

    private static class ViewHolder {
        public ImageView listIcon;
        public TextView listTitle;
        public RadioButton radioButton;
    }
    private AddMovieToListDialogListsItemContainer addMovieToListDialogListsItemContainer;
    private Context context;
    private LayoutInflater layoutInflater;

    public AddMovieToListDialogListsItemAdapter() {

    }

    public AddMovieToListDialogListsItemAdapter(Context context, AddMovieToListDialogListsItemContainer addMovieToListDialogListsItemContainer) {
        this.context = context;
        this.addMovieToListDialogListsItemContainer = addMovieToListDialogListsItemContainer;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return addMovieToListDialogListsItemContainer.getAddMovieToListDialogListsItemArrayList().size();
    }

    @Override
    public Object getItem(int position) {
        return addMovieToListDialogListsItemContainer.getAddMovieToListDialogListsItemArrayList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.add_movie_to_list_dialog_item, null);
            viewHolder = new ViewHolder();

            viewHolder.listTitle = (TextView)convertView.findViewById(R.id.add_movie_to_list_dialog_list_title);
            viewHolder.listIcon = (ImageView)convertView.findViewById(R.id.add_movie_to_list_dialog_list_icon);
            viewHolder.radioButton = (RadioButton)convertView.findViewById(R.id.add_movie_to_list_dialog_list_radio_button);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.listTitle.setText(addMovieToListDialogListsItemContainer.getAddMovieToListDialogListsItemArrayList().get(position).getTitle());
        viewHolder.listIcon.setImageResource(addMovieToListDialogListsItemContainer.getAddMovieToListDialogListsItemArrayList().get(position).getIcon());

        return convertView;
    }
}
