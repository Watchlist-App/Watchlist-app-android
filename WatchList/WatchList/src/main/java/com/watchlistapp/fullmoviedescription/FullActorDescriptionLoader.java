package com.watchlistapp.fullmoviedescription;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.watchlistapp.searchresults.SearchResultsItemAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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

    private String actorId;

    // Views
    private ImageView actorAvatarImageView;
    private TextView actorNameTextView;
    private TextView actorBirthdayTextView;
    private TextView actorPlaceOfBirthTextView;

    public FullActorDescriptionLoader(String actorId, ImageView actorAvatarImageView, TextView actorNameTextView, TextView actorBirthdayTextView, TextView actorPlaceOfBirthTextView) {
        this.actorId = actorId;
        this.actorAvatarImageView = actorAvatarImageView;
        this.actorNameTextView = actorNameTextView;
        this.actorBirthdayTextView = actorBirthdayTextView;
        this.actorPlaceOfBirthTextView = actorPlaceOfBirthTextView;
    }

    @Override
    protected void onPostExecute(FullActorDescription fullActorDescription) {
        FullActorAvatarLoader fullActorAvatarLoader = new FullActorAvatarLoader(fullActorDescription.getProfilePath(), FullActorAvatarLoader.LARGE, actorAvatarImageView);
        fullActorAvatarLoader.execute();
        actorNameTextView.setText("Name: " + fullActorDescription.getName());
        actorBirthdayTextView.setText("Birthday: " + SearchResultsItemAdapter.convertDate(fullActorDescription.getBirthday()));
        actorPlaceOfBirthTextView.setText("Place of birth: " + fullActorDescription.getPlaceOfBirth());
    }

    @Override
    protected FullActorDescription doInBackground(String... params) {
        FullActorDescription fullActorDescription = null;
        String url = BASE_URL + actorId + "?" + API_KEY_TITLE + "=" + API_KEY;
        JSONObject jsonObject = getJSONObject(url);
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
        } catch(JSONException exception) {
            exception.printStackTrace();
        }

        return fullActorDescription;
    }

    private JSONObject getJSONObject(String url) {
        InputStream inputStream = getInputStream(url);
        String jsonString = convertInputStreamToString(inputStream);

        JSONObject jsonObject = null;
        // Try to parse json string
        try {
            jsonObject = new JSONObject(jsonString);
        }catch(JSONException exception) {
            exception.printStackTrace();
        }
        return jsonObject;
    }

    // This method get InputStream from url
    private InputStream getInputStream(String url) {
        InputStream inputStream = null;

        // Making HTTP request
        try {
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        } catch(ClientProtocolException exception) {
            exception.printStackTrace();
        } catch(IOException exception) {
            exception.printStackTrace();
        } catch(IllegalStateException exception) {
            exception.printStackTrace();
        }
        return inputStream;
    }

    // This method converts InputStream to json string
    private String convertInputStreamToString(InputStream inputStream) {
        String jsonString = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            inputStream.close();
            jsonString = stringBuilder.toString();
        } catch(UnsupportedEncodingException exception) {
            exception.printStackTrace();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
        return jsonString;
    }
}
