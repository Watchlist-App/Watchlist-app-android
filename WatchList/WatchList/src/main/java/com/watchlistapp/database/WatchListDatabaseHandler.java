package com.watchlistapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.watchlistapp.authorization.LoggedInUser;
import com.watchlistapp.authorization.LoggedInUserContainer;
import com.watchlistapp.watchlistserver.MovieList;
import com.watchlistapp.watchlistserver.MovieListContainer;

/**
 * Created by VEINHORN on 20/12/13.
 */
public class WatchListDatabaseHandler extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "watchdb2";
    public final static int DATABASE_VERSION = 1;

    // Commands that uses very often
    public final static String DATABASE_DROP_TABLE = "DROP TABLE IF EXISTS ";

    // "Users" table
    public final static String TABLE_USERS = "users";
    public final static String TABLE_USERS_ID = "user_id";
    public final static String TABLE_USERS_EMAIL = "user_email";
    public final static String TABLE_USERS_NAME = "user_name";
    public final static String TABLE_USERS_PASSWORD = "user_password";
    public final static String TABLE_USERS_DATE = "user_date";

    // "Playlists" table
    public final static String TABLE_USER_PLAYLISTS = "playlists";
    public final static String TABLE_USER_PLAYLISTS_ID = "playlists_id";
    public final static String TABLE_USER_PLAYLISTS_TITLE = "playlists_title";

    // "UserPlaylistsRef" table
    public final static String TABLE_USER_PLAYLISTS_REFERENCES = "userplaylistsref";
    public final static String TABLE_USER_PLAYLISTS_REFERENCES_ID = "reference_id";
    public final static String TABLE_USER_PLAYLISTS_REFERENCES_EMAIL = "reference_email";

    private Context context;

    public WatchListDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // The "user" table creation
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + " ( " +
                TABLE_USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_USERS_EMAIL + " TEXT, " +
                TABLE_USERS_NAME + " TEXT, " +
                TABLE_USERS_PASSWORD + " TEXT, " +
                TABLE_USERS_DATE + " TEXT)";

        // The "playlists" table creation
        String CREATE_PLAYLISTS_TABLE = "CREATE TABLE " + TABLE_USER_PLAYLISTS + " ( " +
                TABLE_USER_PLAYLISTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_USER_PLAYLISTS_TITLE + " TEXT)";

        // The "user playlists references" table creation
        String CREATE_USER_PLAYLISTS_REFERENCES_TABLE = "CREATE TABLE " + TABLE_USER_PLAYLISTS_REFERENCES + " ( " +
                TABLE_USER_PLAYLISTS_REFERENCES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_USER_PLAYLISTS_REFERENCES_EMAIL + " TEXT)";
        database.execSQL(CREATE_USER_TABLE);
        database.execSQL(CREATE_PLAYLISTS_TABLE);
        database.execSQL(CREATE_USER_PLAYLISTS_REFERENCES_TABLE);
    }

    // This method delete user from database including all data that he has
    public void deleteUserContent(LoggedInUser loggedInUser) {
        SQLiteDatabase database = this.getWritableDatabase();

        // Select all rows with such email that this user has

        Cursor cursor = database.query(TABLE_USER_PLAYLISTS_REFERENCES, new String[] { TABLE_USER_PLAYLISTS_REFERENCES_ID },
                TABLE_USER_PLAYLISTS_REFERENCES_EMAIL + "=?",
                new String[] { loggedInUser.getEmail() }, null, null, null, null);

        // Delete all playlists of this user from PLAYLISTS table
        if(cursor.moveToFirst()) {
            do {
                database.delete(TABLE_USER_PLAYLISTS, TABLE_USER_PLAYLISTS_ID + "=?",
                        new String[] { cursor.getString(0) });
            }while(cursor.moveToNext());
        }

        // Delete all references for this user
        database.delete(TABLE_USER_PLAYLISTS_REFERENCES, TABLE_USER_PLAYLISTS_REFERENCES_EMAIL + "=?",
                new String[] { loggedInUser.getEmail() });

        // Delete from user table
        database.delete(TABLE_USERS, TABLE_USERS_EMAIL + "=?",
                new String[] { loggedInUser.getEmail() });

        database.close();
    }

    // This method delete all users from database
    public void deleteAllUsers() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_USERS, null, null);
        database.delete(TABLE_USER_PLAYLISTS, null, null);
        database.delete(TABLE_USER_PLAYLISTS_REFERENCES, null, null);
        database.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldDatabaseVersion, int newDatabaseVersion) {
        // Drop older table if exists
        database.execSQL(DATABASE_DROP_TABLE + TABLE_USERS);
        database.execSQL(DATABASE_DROP_TABLE + TABLE_USER_PLAYLISTS);
        // Creates fresh database
        this.onCreate(database);
    }

    // This method adds new user to database
    public void addUser(LoggedInUser loggedInUser) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_USERS_EMAIL, loggedInUser.getEmail());
        contentValues.put(TABLE_USERS_NAME, loggedInUser.getName());
        contentValues.put(TABLE_USERS_PASSWORD, loggedInUser.getPassword());
        contentValues.put(TABLE_USERS_DATE, loggedInUser.getDate());

        // Inserting row
        database.insert(TABLE_USERS, null, contentValues);
        database.close(); // closing database connection
    }

    // This method returns all users that database has
    public LoggedInUserContainer getAllUsers() {
        LoggedInUserContainer loggedInUserContainer = new LoggedInUserContainer();
        // Select all query
        String selectAllQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectAllQuery, null);

        // Looping through all rows and adding to list
        if(cursor.moveToFirst()) {
            do {
                LoggedInUser loggedInUser = new LoggedInUser();
                loggedInUser.setEmail(cursor.getString(1));
                loggedInUser.setName(cursor.getString(2));
                loggedInUser.setPassword(cursor.getString(3));
                loggedInUser.setDate(cursor.getString(4));

                loggedInUserContainer.getLoggedInUserArrayList().add(loggedInUser);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return loggedInUserContainer;
    }

    // This method returns total number of users in database
    public int getUsersNumber() {
        String numberQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(numberQuery, null);

        return cursor.getCount();
    }

    // This method return true if the database has such user
    public boolean isSuchUser(LoggedInUser loggedInUser) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_USERS, new String[] { TABLE_USERS_EMAIL },
                TABLE_USERS_EMAIL + "=?",
                new String[] { loggedInUser.getEmail() }, null, null, null, null);

        if(cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    // This method update existing user in database
    public void updateUser(LoggedInUser loggedInUser) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_USERS_EMAIL, loggedInUser.getEmail());
        contentValues.put(TABLE_USERS_NAME, loggedInUser.getName());
        contentValues.put(TABLE_USERS_PASSWORD, loggedInUser.getPassword());
        contentValues.put(TABLE_USERS_DATE, loggedInUser.getDate());

        // Updating row
        database.update(TABLE_USERS, contentValues, TABLE_USERS_EMAIL + " = ?",
                new String[] { loggedInUser.getEmail() });
        database.close();
    }

    // This method updates user content (simple realization) -- all content rewrites
    public void updateUserContent(LoggedInUser loggedInUser) {
        deleteUserContent(loggedInUser);
        addUserContent(loggedInUser);
    }

    // This method add user content
    public void addUserContent(LoggedInUser loggedInUser) {
        addUser(loggedInUser);
        addPlaylists(loggedInUser);
        addReferencesBetweenUsersAndPlaylists(loggedInUser);
    }

    // This method adds list to the table
    public void addPlaylists(LoggedInUser loggedInUser) {
        SQLiteDatabase database = this.getWritableDatabase();

        for(int i = 0; i < loggedInUser.getMovieListContainer().getMovieListArrayList().size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE_USER_PLAYLISTS_TITLE, loggedInUser.getMovieListContainer().getMovieListArrayList().get(i).getTitle());
            database.insert(TABLE_USER_PLAYLISTS, null, contentValues);
        }

        database.close();
    }

    // This method returns all playlists from database
    public MovieListContainer getAllPlaylists() {
        SQLiteDatabase database = this.getReadableDatabase();
        MovieListContainer movieListContainer = new MovieListContainer();
        String selectAllQuery = "SELECT * FROM " + TABLE_USER_PLAYLISTS;
        Cursor cursor = database.rawQuery(selectAllQuery, null);

        if(cursor.moveToFirst()) {
            do {
                MovieList movieList = new MovieList();
                movieList.setTitle(cursor.getString(1));
                movieListContainer.getMovieListArrayList().add(movieList);
            }while(cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return movieListContainer;
    }

    // This method returns the overall number of playlists
    public int getPlaylistsNumber() {
        String numberQuery = "SELECT * FROM " + TABLE_USER_PLAYLISTS;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(numberQuery, null);
        return cursor.getCount();
    }

    // This method adds references to the table
    public void addReferencesBetweenUsersAndPlaylists(LoggedInUser loggedInUser) {
        SQLiteDatabase database = this.getWritableDatabase();

        for(int i = 0; i < loggedInUser.getMovieListContainer().getMovieListArrayList().size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE_USER_PLAYLISTS_REFERENCES_EMAIL, loggedInUser.getEmail());
            database.insert(TABLE_USER_PLAYLISTS_REFERENCES, null, contentValues);
        }

        database.close();
    }

    // This method returns total number of references between user and playlists table
    public int getReferencesNumberBetweenUsersAndPlaylistTables() {
        String numberQuery = "SELECT * FROM " + TABLE_USER_PLAYLISTS_REFERENCES;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(numberQuery, null);
        return cursor.getCount();
    }
}