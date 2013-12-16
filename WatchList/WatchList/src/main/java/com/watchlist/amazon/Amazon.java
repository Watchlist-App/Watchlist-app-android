package com.watchlist.amazon;

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

/**
 * Created by VEINHORN on 14/12/13.
 */
public class Amazon extends AsyncTask<String, Integer, AmazonContainer> {

    private final static String BASE_URL = "http://watchlist-app-server.herokuapp.com/amazon/dvds?title";

    private final static String API_DETAIL_PAGE_URL_TITLE = "DetailPageURL";


    private InputStream inputStream;
    private String jsonString;
    private JSONArray jsonArray;
    private String movieTitle;

    private Context context;
    private AmazonItemAdapter amazonItemAdapter;
    private AmazonContainer amazonContainer;

    public Amazon() {

    }

    public Amazon(Context context, AmazonItemAdapter amazonItemAdapter, AmazonContainer amazonContainer) {
        this.context = context;
        this.amazonItemAdapter = amazonItemAdapter;
        this.amazonContainer = amazonContainer;
    }

    @Override
    protected void onPostExecute(AmazonContainer amazonContainer) {
        amazonItemAdapter.notifyDataSetChanged();
    }

    @Override
    protected AmazonContainer doInBackground(String... params) {
        movieTitle = "Iron man";
        String url = BASE_URL + movieTitle;
        url = url.replaceAll(" ", "%20");
        jsonArray = getJSONArray(url);
        amazonContainer = parseJSONArray(jsonArray);
        /*
        for(int i = 0; i < 10; i++) {
            AmazonElement amazonElement = new AmazonElement();
            amazonElement.setDetailPageUrl("boris korogvich");
            amazonContainer.getAmazonElementArrayList().add(amazonElement);
        }
        */
        return amazonContainer;
    }

    public JSONArray getJSONArray(String url) {
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
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch(IllegalStateException exception) {
            exception.printStackTrace();
        }

        // Convert input stream to string
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            inputStream.close();
            jsonString = stringBuilder.toString();
        } catch(IOException exception) {
            exception.printStackTrace();
        }

        // Try parse json string into json object
        try {
            jsonArray = new JSONArray(jsonString);
        } catch(JSONException exception) {
            exception.printStackTrace();
        }

        return jsonArray;
    }

    // Parse json to AmazonContainer
    public AmazonContainer parseJSONArray(JSONArray jsonArray) {
        AmazonContainer amazonContainer = new AmazonContainer();
        // If no users on server
        if(jsonArray == null) {
            return null;
        }

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = null;
            // Check here after if no elements or no references in json app can crashes
            try {
                jsonObject = jsonArray.getJSONObject(i);

                AmazonElement amazonElement = new AmazonElement();
                amazonElement.setDetailPageUrl("2pac"); /*jsonObject.getString(API_DETAIL_PAGE_URL_TITLE)*/

                amazonContainer.getAmazonElementArrayList().add(amazonElement);
            } catch(JSONException exception) {
                exception.printStackTrace();
            }
        }

        return amazonContainer;
    }
}
