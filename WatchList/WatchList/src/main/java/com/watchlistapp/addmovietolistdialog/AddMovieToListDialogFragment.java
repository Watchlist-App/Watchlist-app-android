package com.watchlistapp.addmovietolistdialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;

import com.watchlistapp.R;
import com.watchlistapp.database.WatchListDatabaseHandler;
import com.watchlistapp.watchlistserver.MovieList;
import com.watchlistapp.watchlistserver.MovieListContainer;

/**
 * Created by VEINHORN on 29/12/13.
 */
public class AddMovieToListDialogFragment extends DialogFragment {

    private ListView listView;
    private AddMovieToListDialogListsItemAdapter addMovieToListDialogListsItemAdapter;
    private AddMovieToListDialogListsItemContainer addMovieToListDialogListsItemContainer;
    private Button okButton;
    private Button cancelButton;
    private Dialog dialog;

    /*
     * Create a new instance of AddMovieToListDialogFragment, providing "num"
     * as an argument
     */
    public static AddMovieToListDialogFragment newInstance() {
        AddMovieToListDialogFragment addMovieToListDialogFragment = new AddMovieToListDialogFragment();

        return addMovieToListDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity());
        dialog.setTitle("Add movie to list");
        return dialog;
    }

    private void fillListContainer(AddMovieToListDialogListsItemContainer addMovieToListDialogListsItemContainer) {
        WatchListDatabaseHandler watchListDatabaseHandler = new WatchListDatabaseHandler(getActivity());
        MovieListContainer movieListContainer = watchListDatabaseHandler.getAllPlaylists();

        for(MovieList movieList : movieListContainer.getMovieListArrayList()) {
            addMovieToListDialogListsItemContainer.getAddMovieToListDialogListsItemArrayList().add(new AddMovieToListDialogListsItem(movieList.getTitle(), R.drawable.mylists));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_movie_to_dialog_layout, container, false);

        listView = (ListView)view.findViewById(R.id.add_movie_to_list_dialog_fragment_list_view);

        addMovieToListDialogListsItemContainer = new AddMovieToListDialogListsItemContainer();
        fillListContainer(addMovieToListDialogListsItemContainer);
        addMovieToListDialogListsItemAdapter = new AddMovieToListDialogListsItemAdapter(getActivity(), addMovieToListDialogListsItemContainer);
        listView.setAdapter(addMovieToListDialogListsItemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RadioButton radioButton = (RadioButton)view.findViewById(R.id.add_movie_to_list_dialog_list_radio_button);
                radioButton.setText("test");

                //Toast toast = Toast.makeText(getActivity(), addMovieToListDialogListsItemContainer.getAddMovieToListDialogListsItemArrayList().get(position).getTitle(), Toast.LENGTH_SHORT);
                //toast.show();
            }
        });

        okButton = (Button)view.findViewById(R.id.add_movie_to_list_dialog_fragment_ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroyView();
                onDestroy();
            }
        });

        return view;
    }
}
