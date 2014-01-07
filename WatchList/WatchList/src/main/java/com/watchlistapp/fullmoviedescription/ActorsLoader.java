package com.watchlistapp.fullmoviedescription;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
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

public class ActorsLoader extends AsyncTask<String, Integer, ActorContainer> {

    private final static String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private final static String API_KEY = "2b7854ef68a3c274a0f804c031285c46";
    private final static String API_KEY_TITLE = "api_key";
    private final static String API_APPEND_TO_RESPONSE = "append_to_response=trailers,credits";
    private final static String API_CREDITS_TITLE = "credits";
    private final static String API_CAST_TITLE = "cast";

    private final static String API_ID_TITLE = "id";
    private final static String API_NAME_TITLE = "name";
    private final static String API_CHARACTER_TITLE = "character";
    private final static String API_ORDER_TITLE = "order";
    private final static String API_CAST_ID_TITLE = "cast_id";
    private final static String API_PROFILE_PATH_TITLE = "profile_path";

    private Context context;
    private String movieId;
    private ActorItemsListAdapter actorItemsListAdapter;
    private ActorItemsContainer actorItemsContainer;

    public ActorsLoader(Context context, String movieId, ActorItemsListAdapter actorItemsListAdapter, ActorItemsContainer actorItemsContainer) {
        this.context = context;
        this.movieId = movieId;
        this.actorItemsListAdapter = actorItemsListAdapter;
        this.actorItemsContainer = actorItemsContainer;
    }

    @Override
    protected void onPostExecute(ActorContainer actorContainer) {
        for(Actor actor : actorContainer.getActorArrayList()) {
            ActorItem actorItem = new ActorItem();
            actorItem.setName(actor.getName());
            actorItem.setCharacter(actor.getCharacter());
            actorItemsContainer.getActorItemArrayList().add(actorItem);
        }
        this.actorItemsListAdapter.notifyDataSetChanged();
        //this.actorContainer = actorContainer;
        //this.actorItemsListAdapter.notifyDataSetChanged();
    }

    @Override
    protected ActorContainer doInBackground(String... params) {
        ActorContainer actorContainer = null;
        String url = BASE_URL + movieId + "?" + API_KEY_TITLE + "=" + API_KEY + "&" + API_APPEND_TO_RESPONSE;
        JSONObject jsonObject = getJSONObject(url);
        actorContainer = parseJSONObject(jsonObject);
        return actorContainer;
    }

    private ActorContainer parseJSONObject(JSONObject jsonObject) {
        ActorContainer actorContainer = new ActorContainer();

        try {
            JSONObject creditsJsonObject = jsonObject.getJSONObject(API_CREDITS_TITLE);
            JSONArray castJsonArray = creditsJsonObject.getJSONArray(API_CAST_TITLE);
            for(int i = 0; i < castJsonArray.length(); i++) {
                JSONObject actorJsonObject = castJsonArray.getJSONObject(i);
                Actor actor = new Actor();
                actor.setId(actorJsonObject.getString(API_ID_TITLE));
                actor.setCharacter(actorJsonObject.getString(API_CHARACTER_TITLE));
                actor.setName(actorJsonObject.getString(API_NAME_TITLE));
                actor.setOrder(actorJsonObject.getString(API_ORDER_TITLE));
                actor.setCastId(actorJsonObject.getString(API_CAST_ID_TITLE));
                actor.setProfile_path(actorJsonObject.getString(API_PROFILE_PATH_TITLE));
                actorContainer.getActorArrayList().add(actor);
            }
        } catch(JSONException exception) {
            exception.printStackTrace();
        }
        return actorContainer;
    }

    private JSONObject getJSONObject(String url) {
        InputStream inputStream = getInputStream(url);
        String jsonString = convertInputStreamToString(inputStream);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch(JSONException exception) {
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
