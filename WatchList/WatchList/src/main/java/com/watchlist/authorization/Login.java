package com.watchlist.authorization;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import com.watchlist.RecentActivity;

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
 * Created by VEINHORN on 25/11/13.
 */
public class Login extends AsyncTask<String, Integer, User> {
    private final static String BASE_URL = "http://watchlist-app-server.herokuapp.com/user";
    private final static String API_LOGIN_NAME_TITLE = "name";
    private final static String API_LOGIN_EMAIL_TITLE = "email";
    private final static String API_LOGIN_PASSWORD_TITLE = "password";
    private final static String API_LOGIN_CONFIRMATION_TITLE = "confirmation";
    private final static String API_LOGIN_CSRF_TITLE = "_csrf";
    private final static String API_LOGIN_CREATED_AT_TITLE = "createdAt";
    private final static String API_LOGIN_UPDATED_AT_TITLE = "updatedAt";
    private final static String API_LOGIN_ID_TITLE = "id";

    private TextView textView;
    private TextView testTextView;
    private InputStream inputStream;
    private String json;

    public UserContainer getUserContainer() {
        return userContainer;
    }

    private UserContainer userContainer;
    private JSONArray jsonArray;
    private boolean isSuchUser;
    private User enteredUser;
    private Context context;
    private ProgressDialog progressDialog;
    private Activity loginActivity;


    public Login(User enteredUser, Context context, Activity activity) {
        this.enteredUser = enteredUser;
        this.context = context;
        this.loginActivity = activity;
        this.progressDialog = ProgressDialog.show(context, "Log in", "Loading. Please wait...", true);
    }

    @Override
    protected void onPostExecute(User user) {
        progressDialog.hide();
        // Such for test, while i make recent activity
        /*
        // If user don't log in
        if(user == null) {
            Toast toast = Toast.makeText(loginActivity, context.getString(R.string.activity_login_try_to_type_again), Toast.LENGTH_SHORT);
            toast.show();
        } else { // If user log in
            // Gets current date
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
            String currentDate = simpleDateFormat.format(new Date());

            // Check if the database contains such user
            LogedInUser logedInUser = new LogedInUser(user, currentDate);
            WatchListSQLiteOpenHelper watchListSQLiteOpenHelper = new WatchListSQLiteOpenHelper(context);
            // Rewrite information about user to database
            if(watchListSQLiteOpenHelper.isUserTableIsEmpty()) {
                watchListSQLiteOpenHelper.addUser(logedInUser);
            } else {
                watchListSQLiteOpenHelper.updateUser(logedInUser);
            }
            Intent intent = new Intent(loginActivity, RecentActivity.class);
            loginActivity.startActivity(intent);
            loginActivity.finish();
        }
        */
        Intent intent = new Intent(loginActivity, RecentActivity.class);
        loginActivity.startActivity(intent);
        loginActivity.finish();
    }

    @Override
    protected User doInBackground(String... params) {
        String url = BASE_URL;
        jsonArray = getJSONArray(url);
        userContainer = parseJSONArray(jsonArray);
        // Checks that such user are in container that we get from web server
        isSuchUser = userContainer.isSuchUser(enteredUser);

        if(isSuchUser) {
            return enteredUser;
        } else {
            return null;
        }
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
        } catch(IOException exception) {
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
            json = stringBuilder.toString();
        } catch(IOException exception) {
            exception.printStackTrace();
        }

        // Try parse json string
        try {
            jsonArray = new JSONArray(json);
        } catch(JSONException exception) {
            exception.printStackTrace();
        }

        return jsonArray;
    }

    // Parse json into UserContainer
    public UserContainer parseJSONArray(JSONArray jsonArray) {
        userContainer = new UserContainer();

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = null;

            try {
                // Get json object from json array
                jsonObject = jsonArray.getJSONObject(i);

                User user = new User();
                // Set json fields to User object
                user.setName(jsonObject.getString(API_LOGIN_NAME_TITLE));
                user.setEmail(jsonObject.getString(API_LOGIN_EMAIL_TITLE));
                user.setPassword(jsonObject.getString(API_LOGIN_PASSWORD_TITLE));
                //user.setConfirmation(jsonObject.getString(API_LOGIN_CONFIRMATION_TITLE));
                //user.set_csrf(jsonObject.getString(API_LOGIN_CSRF_TITLE));
                //user.setCreatedAt(jsonObject.getString(API_LOGIN_CREATED_AT_TITLE));
                //user.setUpdatedAt(jsonObject.getString(API_LOGIN_UPDATED_AT_TITLE));
                //user.setId(jsonObject.getString(API_LOGIN_ID_TITLE));

                //Write user to user container
                userContainer.getUsersArrayList().add(user);
            } catch(JSONException exception) {
                exception.printStackTrace();
            }
        }
        return userContainer;
    }

    public User getEnteredUser() {
        return enteredUser;
    }

    public boolean getIsSuchUser() {
        return isSuchUser;
    }
}