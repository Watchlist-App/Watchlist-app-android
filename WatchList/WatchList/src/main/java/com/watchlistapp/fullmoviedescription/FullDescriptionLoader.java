package com.watchlistapp.fullmoviedescription;

import android.content.Context;
import android.os.AsyncTask;

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
 * Created by VEINHORN on 26/12/13.
 */
public class FullDescriptionLoader extends AsyncTask<String, Integer, MovieDescription> {

    private Context context;
    private String movieId;

    public FullDescriptionLoader(Context context, String movieId) {
        this.context = context;
        this.movieId = movieId;
    }

    @Override
    protected MovieDescription doInBackground(String... params) {
        MovieDescription movieDescription = new MovieDescription();



        return movieDescription;
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
