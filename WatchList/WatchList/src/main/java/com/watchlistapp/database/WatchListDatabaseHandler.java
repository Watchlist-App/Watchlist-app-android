package com.watchlistapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.watchlistapp.authorization.LoggedInUser;
import com.watchlistapp.authorization.LoggedInUserContainer;
import com.watchlistapp.watchlistserver.Movie;
import com.watchlistapp.watchlistserver.MovieContainer;
import com.watchlistapp.watchlistserver.MovieList;
import com.watchlistapp.watchlistserver.MovieListContainer;

import java.util.ArrayList;

/**
 * Created by VEINHORN on 20/12/13.
 */
public class WatchListDatabaseHandler extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "watchdb7";
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
    public final static String TABLE_USER_SERVER_ID = "server_id";

    // "Playlists" table
    public final static String TABLE_USER_PLAYLISTS = "playlists";
    public final static String TABLE_USER_PLAYLISTS_ID = "playlists_id";
    public final static String TABLE_USER_PLAYLISTS_TITLE = "playlists_title";

    // "UserPlaylistsRef" table
    public final static String TABLE_USER_PLAYLISTS_REFERENCES = "userplaylistsref";
    public final static String TABLE_USER_PLAYLISTS_REFERENCES_ID = "reference_id";
    public final static String TABLE_USER_PLAYLISTS_REFERENCES_EMAIL = "reference_email";

    // "Movies" table
    public final static String TABLE_MOVIES = "movies";
    public final static String TABLE_MOVIES_ID = "movies_id";
    public final static String TABLE_MOVIES_THEMOVIEDB_ID = "themoviedb_id";

    // "MovieListRef" table
    public final static String TABLE_MOVIE_LIST_REFERENCES = "movielistref";
    public final static String TABLE_MOVIE_LIST_REFERENCES_ID = "movie_list_ref_id";
    public final static String TABLE_MOVIE_LIST_REFERENCES_TITLE = "movie_list_ref_title";

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
                TABLE_USERS_DATE + " TEXT, " +
                TABLE_USER_SERVER_ID + " TEXT)";

        // The "playlists" table creation
        String CREATE_PLAYLISTS_TABLE = "CREATE TABLE " + TABLE_USER_PLAYLISTS + " ( " +
                TABLE_USER_PLAYLISTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_USER_PLAYLISTS_TITLE + " TEXT)";

        // The "user playlists references" table creation
        String CREATE_USER_PLAYLISTS_REFERENCES_TABLE = "CREATE TABLE " + TABLE_USER_PLAYLISTS_REFERENCES + " ( " +
                TABLE_USER_PLAYLISTS_REFERENCES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_USER_PLAYLISTS_REFERENCES_EMAIL + " TEXT)";

        // The "movies" id table creation
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + " ( " +
                TABLE_MOVIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_MOVIES_THEMOVIEDB_ID + " INTEGER)";

        // The "Movie list ref" table creation
        String CREATE_MOVIE_LIST_REFERENCES_TABLE = "CREATE TABLE " + TABLE_MOVIE_LIST_REFERENCES + " ( " +
                TABLE_MOVIE_LIST_REFERENCES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_MOVIE_LIST_REFERENCES_TITLE + " TEXT)";

        database.execSQL(CREATE_USER_TABLE);
        database.execSQL(CREATE_PLAYLISTS_TABLE);
        database.execSQL(CREATE_USER_PLAYLISTS_REFERENCES_TABLE);
        database.execSQL(CREATE_MOVIES_TABLE);
        database.execSQL(CREATE_MOVIE_LIST_REFERENCES_TABLE);
    }

    // This method delete user from database including all data that he has
    public ArrayList<String> deleteUserContent(LoggedInUser loggedInUser) {
        SQLiteDatabase database = this.getWritableDatabase();

        //Select all ids by email for TABLE_LISTS
        Cursor listIdsCursor = database.query(TABLE_USER_PLAYLISTS_REFERENCES, new String[] { TABLE_USER_PLAYLISTS_REFERENCES_ID },
                TABLE_USER_PLAYLISTS_REFERENCES_EMAIL + "=?", new String[] { loggedInUser.getEmail() }, null, null, null, null);

        // Get playlists titles
        ArrayList<String> playlistsIds = new ArrayList<String>();
        if(listIdsCursor.moveToFirst()) {
            do {
                playlistsIds.add(listIdsCursor.getString(0));
            }while(listIdsCursor.moveToNext());
        }

        ArrayList<String> playlistsTitles = new ArrayList<String>();
        for(String id : playlistsIds) {
            Cursor plalistTitlesCursor = database.query(TABLE_USER_PLAYLISTS, new String[] { TABLE_USER_PLAYLISTS_TITLE },
                    TABLE_USER_PLAYLISTS_ID + "=?", new String[] { id }, null, null, null, null);
            plalistTitlesCursor.moveToFirst();
            playlistsTitles.add(plalistTitlesCursor.getString(0));
        }

        ArrayList<String> moviesIdsForDeleting = new ArrayList<String>();
        for(String id : playlistsTitles) {
            Cursor idsForDeletingCursor = database.query(TABLE_MOVIE_LIST_REFERENCES, new String[] { TABLE_MOVIE_LIST_REFERENCES_ID },
                    TABLE_MOVIE_LIST_REFERENCES_TITLE + "=?", new String[] { id }, null, null, null, null);
            if(idsForDeletingCursor.moveToFirst()) {
                do {
                    moviesIdsForDeleting.add(idsForDeletingCursor.getString(0));
                }while(idsForDeletingCursor.moveToNext());
            }
        }

        for(String id : moviesIdsForDeleting) {
            database.delete(TABLE_MOVIES, TABLE_MOVIES_ID + "=?", new String[] { id });
            database.delete(TABLE_MOVIE_LIST_REFERENCES, TABLE_MOVIE_LIST_REFERENCES_ID + "=?", new String[] { id });
        }

        database.delete(TABLE_USER_PLAYLISTS_REFERENCES, TABLE_USER_PLAYLISTS_REFERENCES_ID + "=?", new String[] { loggedInUser.getEmail() });
        database.delete(TABLE_USERS, TABLE_USERS_EMAIL + "=?", new String[] { loggedInUser.getEmail() });

        database.close();

        return playlistsTitles;
        /*
        if(listIdsCursor.moveToFirst()) {
            do {
                Cursor playlistTitleCursor = database.query(TABLE_USER_PLAYLISTS, new String[] { TABLE_USER_PLAYLISTS_TITLE },
                        TABLE_USER_PLAYLISTS_ID + "=?", new String[] { listIdsCursor.getString(0) }, null, null, null, null);

                if(playlistTitleCursor.moveToFirst()) {
                    do {
                        Cursor movieListsRefIds = database.query(TABLE_MOVIE_LIST_REFERENCES, new String[] { TABLE_MOVIE_LIST_REFERENCES_ID },
                                TABLE_MOVIE_LIST_REFERENCES_TITLE + "=?", new String[] { playlistTitleCursor.getString(0) }, null, null, null, null);

                        if(movieListsRefIds.moveToFirst()) {
                            do {
                                database.delete(TABLE_MOVIE_LIST_REFERENCES, TABLE_MOVIE_LIST_REFERENCES_ID + "=?", new String[] { movieListsRefIds.getString(0) });
                                database.delete(TABLE_MOVIES, TABLE_MOVIES_ID + "=?", new String[] { movieListsRefIds.getString(0) });
                            }while(movieListsRefIds.moveToNext());
                        }
                        // delete lists from playlists table
                        database.delete(TABLE_USER_PLAYLISTS, TABLE_USER_PLAYLISTS_ID + "=?", new String[] { listIdsCursor.getString(0) });

                    }while(playlistTitleCursor.moveToNext());
                }
            }while(listIdsCursor.moveToNext());
        }*/

        // Important not delete
        //database.delete(TABLE_USER_PLAYLISTS_REFERENCES, TABLE_USER_PLAYLISTS_REFERENCES_ID + "=?", new String[] { loggedInUser.getEmail() });
        //database.delete(TABLE_USERS, TABLE_USERS_EMAIL + "=?", new String[] { loggedInUser.getEmail() });

        //database.close();

    }

    // This method delete all users from database
    public void deleteAllUsers() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_USERS, null, null);
        database.delete(TABLE_USER_PLAYLISTS, null, null);
        database.delete(TABLE_USER_PLAYLISTS_REFERENCES, null, null);
        database.delete(TABLE_MOVIES, null, null);
        database.delete(TABLE_MOVIE_LIST_REFERENCES, null, null);
        database.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldDatabaseVersion, int newDatabaseVersion) {
        // Drop older table if exists
        database.execSQL(DATABASE_DROP_TABLE + TABLE_USERS);
        database.execSQL(DATABASE_DROP_TABLE + TABLE_USER_PLAYLISTS);
        database.execSQL(DATABASE_DROP_TABLE + TABLE_USER_PLAYLISTS_REFERENCES);
        database.execSQL(DATABASE_DROP_TABLE + TABLE_MOVIES);
        database.execSQL(DATABASE_DROP_TABLE + TABLE_MOVIE_LIST_REFERENCES);
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
        contentValues.put(TABLE_USER_SERVER_ID, loggedInUser.getServerId());

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
                loggedInUser.setServerId(cursor.getString(5));

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
        contentValues.put(TABLE_USER_SERVER_ID, loggedInUser.getServerId());

        // Updating row
        database.update(TABLE_USERS, contentValues, TABLE_USERS_EMAIL + " = ?",
                new String[] { loggedInUser.getEmail() });
        database.close();
    }

    // This method updates user content (simple realization) -- all content rewrites
    public void updateUserContent(LoggedInUser loggedInUser) {
        deleteAllUsers();
        addUserContent(loggedInUser);
    }

    // This method add user content
    public void addUserContent(LoggedInUser loggedInUser) {
        deleteAllUsers();
        addUser(loggedInUser);
        addPlaylists(loggedInUser);
        addReferencesBetweenUsersAndPlaylists(loggedInUser);
        addMovies(loggedInUser);
        addReferencesBetweenPlaylistsAndMovies(loggedInUser);
    }

    // This method adds list to the table
    public void addPlaylists(LoggedInUser loggedInUser) {
        SQLiteDatabase database = this.getWritableDatabase();

        for(int i = 0; i < loggedInUser.getMovieListContainer().size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE_USER_PLAYLISTS_TITLE, loggedInUser.getMovieListContainer().getMovieList(i).getTitle());
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
                String listTitle = cursor.getString(1);
                movieList.setTitle(listTitle);
                movieList.setMovieContainer(getMovieIdsByListTitle(listTitle));
                movieListContainer.addMovieList(movieList);
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

        for(int i = 0; i < loggedInUser.getMovieListContainer().size(); i++) {
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

    // This method returns all lists that user has
    public MovieListContainer getPlaylistsByEmail(LoggedInUser loggedInUser) {
        SQLiteDatabase database = this.getReadableDatabase();
        MovieListContainer movieListContainer = new MovieListContainer();

        Cursor cursor = database.query(TABLE_USER_PLAYLISTS_REFERENCES, new String[] { TABLE_USER_PLAYLISTS_REFERENCES_ID },
                TABLE_USER_PLAYLISTS_REFERENCES_EMAIL + "=?", new String[] { loggedInUser.getEmail() }, null, null, null, null);

        Cursor playlistsCursor = null;

        if(cursor.moveToFirst()) {
            do {
                playlistsCursor = database.query(TABLE_USER_PLAYLISTS, new String[] { TABLE_USER_PLAYLISTS_TITLE },
                        TABLE_USER_PLAYLISTS_ID + "=?", new String[] { cursor.getString(0) }, null, null, null, null);
                if(playlistsCursor.moveToFirst()) {
                    do {
                        MovieList movieList = new MovieList();
                        movieList.setTitle(playlistsCursor.getString(0));
                        movieListContainer.addMovieList(movieList);
                    }while(playlistsCursor.moveToNext());
                }
            }while(cursor.moveToNext());
        }

        database.close();
        return movieListContainer;
    }

    // This method adds movies to table
    public void addMovies(LoggedInUser loggedInUser) {
        SQLiteDatabase database = this.getWritableDatabase();

        for(MovieList movieList : loggedInUser.getMovieListContainer().getMovieListArrayList()) {
            MovieContainer movieContainer = movieList.getMovieContainer();
            for(Movie movie : movieContainer.getMovieArrayList()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(TABLE_MOVIES_THEMOVIEDB_ID, movie.getId());
                database.insert(TABLE_MOVIES, null, contentValues);
            }
        }
        database.close();
    }

    // This method adds references between playlists and movies tables
    public void addReferencesBetweenPlaylistsAndMovies(LoggedInUser loggedInUser) {
        SQLiteDatabase database = this.getWritableDatabase();

        for(MovieList movieList : loggedInUser.getMovieListContainer().getMovieListArrayList()) {
            MovieContainer movieContainer = movieList.getMovieContainer();
            for(Movie movie : movieContainer.getMovieArrayList()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(TABLE_MOVIE_LIST_REFERENCES_TITLE, movieList.getTitle());
                database.insert(TABLE_MOVIE_LIST_REFERENCES, null, contentValues);
            }
        }
        database.close();
    }

    // This method returns all movies from database
    public MovieContainer getAllMovies() {
        SQLiteDatabase database = this.getReadableDatabase();
        MovieContainer movieContainer = new MovieContainer();
        String selectAllQuery = "SELECT * FROM " + TABLE_MOVIES;
        Cursor cursor = database.rawQuery(selectAllQuery, null);

        if(cursor.moveToFirst()) {
            do {
                movieContainer.addMovie(new Movie(cursor.getString(1)));
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return movieContainer;
    }

    // This method returns the number of all references between lists and movies
    public int getReferencesNumberBetweenPlaylistsAndMovies() {
        String numberQuery = "SELECT * FROM " + TABLE_MOVIE_LIST_REFERENCES;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(numberQuery, null);
        return cursor.getCount();
    }

    // This method returns the total number of movies in table
    public int getMoviesNumber() {
        String numberQuery = "SELECT * FROM " + TABLE_MOVIES;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(numberQuery, null);
        return cursor.getCount();
    }

    // Just for test references between playlists and movies
    public ArrayList<String> getAllListNamesFromReferencesTable() {
        ArrayList<String> names = new ArrayList<String>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectAllQuery = "SELECT * FROM " + TABLE_MOVIE_LIST_REFERENCES;
        Cursor cursor = database.rawQuery(selectAllQuery, null);
        if(cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                names.add(name);
            }while(cursor.moveToNext());
        }
        database.close();
        return names;
    }

    // This method returns movies id for list title
    public MovieContainer getMovieIdsByListTitle(String listTitle) {
        SQLiteDatabase database = this.getReadableDatabase();
        MovieContainer movieContainer = new MovieContainer();

        Cursor cursor = database.query(TABLE_MOVIE_LIST_REFERENCES, new String[] { TABLE_MOVIE_LIST_REFERENCES_ID },
                TABLE_MOVIE_LIST_REFERENCES_TITLE + "=?", new String[] { listTitle }, null, null, null, null);

        if(cursor.moveToFirst()) {
            do {
                Cursor idCursor = database.query(TABLE_MOVIES, new String[] { TABLE_MOVIES_THEMOVIEDB_ID },
                        TABLE_MOVIES_ID + "=?", new String[] { cursor.getString(0) }, null, null, null, null);
                idCursor.moveToFirst();
                movieContainer.addMovie(new Movie(idCursor.getString(0)));
            }while(cursor.moveToNext());
        }
        database.close();
        return movieContainer;
    }
}