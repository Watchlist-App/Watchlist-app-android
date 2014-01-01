package com.watchlistapp.authorization;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.watchlistapp.HomeActivity;
import com.watchlistapp.database.WatchListDatabaseHandler;
import com.watchlistapp.watchlistserver.Movie;
import com.watchlistapp.watchlistserver.MovieList;

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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VEINHORN on 21/12/13.
 */
public class Login extends AsyncTask<String, Integer, User> {

    private final static String BASE_URL = "http://watchlist-app-server.herokuapp.com/user";
    private final static String BASE_URL_EMAIL = "email";
    private final static String BASE_URL_NAME = "name";

    private final static String API_LOGIN_EMAIL_TITLE = "email";
    private final static String API_LOGIN_NAME_TITLE = "name";
    private final static String API_LOGIN_PASSWORD_TITLE = "password";
    private final static String API_LOGIN_LISTS_TITLE = "lists";
    private final static String API_LOGIN_LISTS_LIST_TITLE = "title";
    private final static String API_LOGIN_USER_ID_TITLE = "id";

    // String that contains user name or email
    private String userNameOrEmail;
    private String password;
    private Context context;
    private ProgressDialog progressDialog;
    private User user;
    private Activity activity;

    public Login(Activity activity, Context context, String userNameOrEmail, String password) {
        this.activity = activity;
        this.context = context;
        this.userNameOrEmail = userNameOrEmail;
        this.password = password;

        this.progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        this.progressDialog = ProgressDialog.show(context, "Log in", "Loading. Please wait...", true);
    }

    /*
    In this method we check is the user with such name or email
    Returns null if such user doesn't exist
     */
    @Override
    protected User doInBackground(String... params) {
        String url = BASE_URL + "?" + BASE_URL_EMAIL + "=" + userNameOrEmail;
        url = url.replaceAll(" ", "%20");
        JSONArray jsonArray = getJSONArray(url);
        User user = parseJSONArray(jsonArray);
        this.user = user;

        // If no user with this nickname try to check with email
        if(user == null) {
            String myUrl = BASE_URL + "?" + BASE_URL_NAME + "=" + userNameOrEmail;
            myUrl = myUrl.replaceAll(" ", "%20");
            JSONArray myJsonArray = getJSONArray(myUrl);
            User myUser = parseJSONArray(myJsonArray);
            this.user = myUser;

            if(myUser == null) {
                return null;
            } else {
                return myUser;
            }
        } else { // if user with this nickname exists
            return user;
        }
    }

    @Override
    protected void onPostExecute(User user) {
        this.progressDialog.hide();

        // If no such user
        if(user == null) {
            Toast toast = Toast.makeText(context, "No such user", Toast.LENGTH_SHORT);
            toast.show();
        } else { // check if password correct
            if((user.getEmail().toLowerCase().equals(userNameOrEmail.toLowerCase()) && user.getPassword().toLowerCase().equals(password.toLowerCase()) ||
                    user.getName().toLowerCase().equals(userNameOrEmail.toLowerCase()) && user.getPassword().toLowerCase().equals(password.toLowerCase()))) {

                WatchListDatabaseHandler watchListDatabaseHandler = new WatchListDatabaseHandler(context);
                // Gets current date
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
                String currentDate = simpleDateFormat.format(new Date());

                LoggedInUser loggedInUser = new LoggedInUser(user, currentDate);

                // If database has user with such name
                if(watchListDatabaseHandler.isSuchUser(loggedInUser)) {
                    // Poka ne spiski filjmov tut ne obnovlyayutca
                    watchListDatabaseHandler.updateUserContent(loggedInUser);

                    Intent intent = new Intent(context, HomeActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                } else { // else we add new user to database
                    watchListDatabaseHandler.addUserContent(loggedInUser);
                    Intent intent = new Intent(context, HomeActivity.class);
                    // Adds animation

                    activity.startActivity(intent);
                    activity.finish();
                }
            } else {
                Toast toast = Toast.makeText(context, "Uncorrected user data. Try type again...", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private User parseJSONArray(JSONArray jsonArray) {
        User user = null;

        try {
            // If no users with such email or nickname
            if(jsonArray.length() == 0) {
                return null;
            } else {
                int index = 0; // the index of first user(user with this name always be 1)
                JSONObject jsonObject = jsonArray.getJSONObject(index);

                user = new User();
                user.setEmail(jsonObject.getString(API_LOGIN_EMAIL_TITLE));
                user.setName(jsonObject.getString(API_LOGIN_NAME_TITLE));
                user.setPassword(jsonObject.getString(API_LOGIN_PASSWORD_TITLE));
                user.setServerId(jsonObject.getString(API_LOGIN_USER_ID_TITLE));

                JSONArray jsonArrayMovieLists = jsonObject.getJSONArray(API_LOGIN_LISTS_TITLE);

                JSONObject jsonObjectMovieList = null;
                MovieList movieList = null;
                for(int i = 0; i < jsonArrayMovieLists.length(); i++) {
                    jsonObjectMovieList = jsonArrayMovieLists.getJSONObject(i);
                    movieList = new MovieList();
                    movieList.setTitle(jsonObjectMovieList.getString(API_LOGIN_LISTS_LIST_TITLE));

                    JSONArray jsonArray1 = jsonObjectMovieList.getJSONArray("movies");
                    for(int j = 0; j < jsonArray1.length(); j++) {
                        movieList.getMovieContainer().getMovieArrayList().add(new Movie(jsonArray1.getString(j)));
                    }

                    user.getMovieListContainer().getMovieListArrayList().add(movieList);
                }

                return user;
            }
        } catch(JSONException exception) {
            exception.printStackTrace();
        }
        return user;
    }

    private JSONArray getJSONArray(String url) {
        InputStream inputStream = getInputStream(url);
        String jsonString = convertInputStreamToString(inputStream);

        JSONArray jsonArray = null;
        // Try to parse json string
        try {
            jsonArray = new JSONArray(jsonString);
        } catch(JSONException exception) {
            exception.printStackTrace();
        }
        return jsonArray;
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

    public User getUser() {
        return user;
    }
}
