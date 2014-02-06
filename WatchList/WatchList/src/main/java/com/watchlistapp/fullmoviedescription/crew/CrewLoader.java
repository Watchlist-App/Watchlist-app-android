package com.watchlistapp.fullmoviedescription.crew;

import android.content.Context;
import android.os.AsyncTask;

import com.watchlistapp.fullmoviedescription.crew.Crew;
import com.watchlistapp.fullmoviedescription.crew.CrewContainer;
import com.watchlistapp.fullmoviedescription.crew.CrewItem;
import com.watchlistapp.fullmoviedescription.crew.CrewItemsContainer;
import com.watchlistapp.fullmoviedescription.crew.CrewItemsListAdapter;
import com.watchlistapp.utils.DeveloperKeys;
import com.watchlistapp.utils.RequestsUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by VEINHORN on 08/01/14.
 */
public class CrewLoader extends AsyncTask<String, Integer, CrewContainer> {

    private final static String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private final static String API_KEY_TITLE = "api_key";
    private final static String API_APPEND_TO_RESPONSE = "append_to_response=trailers,credits";
    private final static String API_CREDITS_TITLE = "credits";
    private final static String API_CREW_TITLE = "crew";

    private final static String API_DEPARTMENT_TITLE = "department";
    private final static String API_NAME_TITLE = "name";
    private final static String API_ID_TITLE = "id";
    private final static String API_JOB_TITLE = "job";
    private final static String API_PROFILE_PATH_TITLE = "profile_path";

    private Context context;
    private String movieId;
    private CrewItemsListAdapter crewItemsListAdapter;
    private CrewItemsContainer crewItemsContainer;

    public CrewLoader(Context context, String movieId, CrewItemsListAdapter crewItemsListAdapter, CrewItemsContainer crewItemsContainer) {
        this.context = context;
        this.movieId = movieId;
        this.crewItemsListAdapter = crewItemsListAdapter;
        this.crewItemsContainer = crewItemsContainer;
    }

    @Override
    protected void onPostExecute(CrewContainer crewContainer) {
        for(Crew crew : crewContainer.getCrewArrayList()) {
            if(!crew.getProfilePath().equals("null")) {
                CrewItem crewItem = new CrewItem();
                crewItem.setProfilePath(crew.getProfilePath());
                crewItem.setName(crew.getName());
                crewItem.setJob(crew.getJob());
                crewItem.setId(crew.getId());
                crewItem.setDepartment(crew.getDepartment());
                crewItemsContainer.getCrewItemArrayList().add(crewItem);
            }
        }
        this.crewItemsListAdapter.notifyDataSetChanged();
    }

    @Override
    protected CrewContainer doInBackground(String... params) {
        CrewContainer crewContainer = null;
        String url = BASE_URL + movieId + "?" + API_KEY_TITLE + "=" + DeveloperKeys.THE_MOVIE_DB_DEVELOPER_KEY + "&" + API_APPEND_TO_RESPONSE;
        JSONObject jsonObject = RequestsUtil.getJSONObject(url);
        crewContainer = parseJSONObject(jsonObject);
        return crewContainer;
    }

    private CrewContainer parseJSONObject(JSONObject jsonObject) {
        CrewContainer crewContainer = new CrewContainer();

        try {
            JSONObject creditsJsonObject = jsonObject.getJSONObject(API_CREDITS_TITLE);
            JSONArray crewJsonArray = creditsJsonObject.getJSONArray(API_CREW_TITLE);
            for(int i = 0; i < crewJsonArray.length(); i++) {
                JSONObject crewJsonObject = crewJsonArray.getJSONObject(i);
                Crew crew = new Crew();
                crew.setDepartment(crewJsonObject.getString(API_DEPARTMENT_TITLE));
                crew.setId(crewJsonObject.getString(API_ID_TITLE));
                crew.setJob(crewJsonObject.getString(API_JOB_TITLE));
                crew.setName(crewJsonObject.getString(API_NAME_TITLE));
                crew.setProfilePath(crewJsonObject.getString(API_PROFILE_PATH_TITLE));
                crewContainer.getCrewArrayList().add(crew);
            }
        } catch(JSONException exception) {
            exception.printStackTrace();
        }
        return crewContainer;
    }
}
