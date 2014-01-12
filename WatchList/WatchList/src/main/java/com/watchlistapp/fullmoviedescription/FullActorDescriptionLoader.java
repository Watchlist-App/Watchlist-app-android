package com.watchlistapp.fullmoviedescription;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.makeramen.RoundedImageView;
import com.textjustifylibrary.TextViewEx;
import com.watchlistapp.searchresults.SearchResultsItemAdapter;
import com.watchlistapp.utils.RequestsUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by VEINHORN on 07/01/14.
 */
public class FullActorDescriptionLoader extends AsyncTask<String, Integer, FullActorDescription> {

    private final static String BASE_URL = "http://api.themoviedb.org/3/person/";
    private final static String API_KEY_TITLE = "api_key";
    private final static String API_KEY = "2b7854ef68a3c274a0f804c031285c46";
    private final static String API_PROFILE_PATH_TITLE = "profile_path";
    private final static String API_NAME_TITLE = "name";
    private final static String API_BIRTHDAY_TITLE = "birthday";
    private final static String API_PLACE_OF_BIRTH_TITLE = "place_of_birth";
    private final static String API_HOMEPAGE_TITLE = "homepage";
    private final static String API_BIOGRAPHY_TITLE = "biography";

    private String actorId;

    // Views
    private RoundedImageView actorAvatarImageView;
    private TextView actorNameTextView;
    private TextView actorBirthdayTextView;
    private TextView actorPlaceOfBirthTextView;
    private TextView actorHomePageTextView;
    private TextViewEx actorBiographyTextView;

    public FullActorDescriptionLoader(String actorId, RoundedImageView actorAvatarImageView, TextView actorNameTextView, TextView actorBirthdayTextView, TextView actorPlaceOfBirthTextView,
                                      TextView actorHomePageTextView, TextViewEx actorBiographyTextView) {
        this.actorId = actorId;
        this.actorAvatarImageView = actorAvatarImageView;
        this.actorNameTextView = actorNameTextView;
        this.actorBirthdayTextView = actorBirthdayTextView;
        this.actorPlaceOfBirthTextView = actorPlaceOfBirthTextView;
        this.actorHomePageTextView = actorHomePageTextView;
        this.actorBiographyTextView = actorBiographyTextView;
    }

    @Override
    protected void onPostExecute(FullActorDescription fullActorDescription) {
        FullActorAvatarLoader fullActorAvatarLoader = new FullActorAvatarLoader(fullActorDescription.getProfilePath(), FullActorAvatarLoader.LARGE, actorAvatarImageView);
        fullActorAvatarLoader.execute();
        actorNameTextView.setText("Name: " + fullActorDescription.getName());
        actorBirthdayTextView.setText("Birthday: " + SearchResultsItemAdapter.convertDate(fullActorDescription.getBirthday()));
        actorPlaceOfBirthTextView.setText("Place of birth: " + fullActorDescription.getPlaceOfBirth());
        actorHomePageTextView.setText("Homepage");
        actorHomePageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        actorBiographyTextView.setText(fullActorDescription.getBiography(), true);
    }

    @Override
    protected FullActorDescription doInBackground(String... params) {
        FullActorDescription fullActorDescription = null;
        String url = BASE_URL + actorId + "?" + API_KEY_TITLE + "=" + API_KEY;
        JSONObject jsonObject = RequestsUtil.getJSONObject(url);
        fullActorDescription = parseJSONObject(jsonObject);
        return fullActorDescription;
    }

    private FullActorDescription parseJSONObject(JSONObject jsonObject) {
        FullActorDescription fullActorDescription = new FullActorDescription();

        try {
            fullActorDescription.setProfilePath(jsonObject.getString(API_PROFILE_PATH_TITLE));
            fullActorDescription.setName(jsonObject.getString(API_NAME_TITLE));
            fullActorDescription.setBirthday(jsonObject.getString(API_BIRTHDAY_TITLE));
            fullActorDescription.setPlaceOfBirth(jsonObject.getString(API_PLACE_OF_BIRTH_TITLE));
            fullActorDescription.setHomePage(jsonObject.getString(API_HOMEPAGE_TITLE));
            fullActorDescription.setBiography(jsonObject.getString(API_BIOGRAPHY_TITLE));
        } catch(JSONException exception) {
            exception.printStackTrace();
        }

        return fullActorDescription;
    }
}
