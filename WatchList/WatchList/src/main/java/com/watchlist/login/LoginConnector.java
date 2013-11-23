package com.watchlist.login;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by VEINHORN on 14/11/13.
 *
 * This class connects to watch app api and returns json object
 */
public class LoginConnector {
    private final static String URL = "http://watchlist-app-server.herokuapp.com/user";

    private final static String PARAMETER_NAME = "name";
    private final static String PARAMETER_EMAIL = "email";
    private final static String PARAMETER_PASSWORD = "password";

    // JSON node names
    private final static String TAG_NAME = "name";
    private final static String TAG_EMAIL = "email";
    private final static String TAG_PASSWORD = "password";

    private InputStream inputStream = null;
    private JSONObject jsonObject = null;
    private String json = "";

    public LoginConnector() {

    }

    public JSONObject getJSONObject() {
        return jsonObject;
    }

    public JSONObject getJsonFromUrl(String url) {
        // Making http request
        try {
            // default http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

        } catch(IOException exception) {
            exception.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            inputStream.close();
            json = stringBuilder.toString();
        } catch(UnsupportedEncodingException exception) {
            exception.printStackTrace();
        } catch(IOException exception) {
            exception.printStackTrace();
        }

        // try parse the string to a JSON object
        try {
            jsonObject = new JSONObject(json);
        } catch(JSONException exception) {
            exception.printStackTrace();
        }
        // return json object
        return jsonObject;
    }

    public String checkIsLogin(String email, String nickname, String password) {
        String url = URL + "?" + PARAMETER_NAME + "=" + email + "&" + PARAMETER_EMAIL + "=" + nickname + "&" + PARAMETER_PASSWORD + "=" + password;
        JSONObject myJsonObject = getJsonFromUrl(url);
        String result = "";

        try {
            result += myJsonObject.getString(TAG_NAME);
            result += " ";
            result += myJsonObject.getString(TAG_EMAIL);
            result += " ";
            result += myJsonObject.getString(TAG_PASSWORD);
        } catch(JSONException exception) {
            exception.printStackTrace();
        }

        return result;
    }
}
