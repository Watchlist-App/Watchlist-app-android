package com.watchlistapp.fullmoviedescription;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Display;
import android.view.WindowManager;

import com.watchlistapp.utils.DeveloperKeys;
import com.watchlistapp.utils.RequestsUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by VEINHORN on 07/01/14.
 */

public class ActorsLoader extends AsyncTask<String, Integer, ActorContainer> {

    private final static String BASE_URL = "http://api.themoviedb.org/3/movie/";
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
            if(!actor.getProfile_path().equals("null")) {
                ActorItem actorItem = new ActorItem();
                actorItem.setProfile_path(actor.getProfile_path());
                actorItem.setName(actor.getName());
                actorItem.setCharacter(actor.getCharacter());
                actorItem.setId(actor.getId());
                actorItemsContainer.getActorItemArrayList().add(actorItem);
            }
        }
        //this.actorItemsListAdapter.notifyDataSetChanged();

        // Get the screen size(height and width)
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        // Here I use old methods
        int displayWidth = display.getWidth();
        int displayHeight = display.getHeight();

        String imageSize = null;
        if((displayWidth == 480 && displayHeight == 800) || (displayWidth == 540 && displayHeight == 960)) {
            imageSize = ActorAvatarLoader.SMALL;
        } else {
            imageSize = ActorAvatarLoader.BIG;
        }

        for(int i = 0; i < actorItemsContainer.getActorItemArrayList().size(); i++) {
            ActorAvatarLoader actorAvatarLoader = new ActorAvatarLoader(actorItemsListAdapter, actorItemsContainer, i,
                    actorItemsContainer.getActorItemArrayList().get(i).getProfile_path(), imageSize);
            actorAvatarLoader.execute();
        }
    }

    @Override
    protected ActorContainer doInBackground(String... params) {
        ActorContainer actorContainer = null;
        String url = BASE_URL + movieId + "?" + API_KEY_TITLE + "=" + DeveloperKeys.THE_MOVIE_DB_DEVELOPER_KEY + "&" + API_APPEND_TO_RESPONSE;
        JSONObject jsonObject = RequestsUtil.getJSONObject(url);
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
}
