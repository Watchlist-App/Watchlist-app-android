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
 * Created by VEINHORN on 08/01/14.
 */
public class CrewLoader extends AsyncTask<String, Integer, CrewContainer> {

    private final static String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private final static String API_KEY = "2b7854ef68a3c274a0f804c031285c46";
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

        for(int i = 0; i < crewItemsContainer.getCrewItemArrayList().size(); i++) {
            CrewAvatarLoader crewAvatarLoader = new CrewAvatarLoader(crewItemsListAdapter, crewItemsContainer, i,
                crewItemsContainer.getCrewItemArrayList().get(i).getProfilePath(), CrewAvatarLoader.BIG);
            crewAvatarLoader.execute();
        }
    }

    @Override
    protected CrewContainer doInBackground(String... params) {
        CrewContainer crewContainer = null;
        String url = BASE_URL + movieId + "?" + API_KEY_TITLE + "=" + API_KEY + "&" + API_APPEND_TO_RESPONSE;
        JSONObject jsonObject = getJSONObject(url);
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
