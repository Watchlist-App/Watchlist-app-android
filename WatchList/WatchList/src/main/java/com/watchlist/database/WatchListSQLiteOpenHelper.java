package com.watchlist.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.watchlist.authorization.LogedInUser;

/**
 * Created by VEINHORN on 28/11/13.
 */
public class WatchListSQLiteOpenHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "WatchlistDB";
    public final static int DATABASE_VERSION = 1;

    // Commands that uses very often
    public final static String DATABASE_DROP_TABLE = "DROP TABLE IF EXISTS ";

    // Constants for USER table
    public final static String TABLE_USER = "users"; // the name of the table
    public final static String TABLE_USER_ID_FOR_TABLE = "user_id";
    public final static String TABLE_USER_NAME = "name";
    public final static String TABLE_USER_EMAIL = "email";
    public final static String TABLE_USER_PASSWORD = "password";
    public final static String TABLE_USER_DATE = "date"; // the last date when user was logged in




    public WatchListSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // The user table creation
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " ( " +
                TABLE_USER_ID_FOR_TABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_USER_NAME + " TEXT, " +
                TABLE_USER_EMAIL + " TEXT, " +
                TABLE_USER_PASSWORD + " TEXT, " +
                TABLE_USER_DATE + " TEXT)";
        database.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldDatabaseVersion, int newDatabaseVersion) {
        // Drop older table if exists
        database.execSQL(DATABASE_DROP_TABLE + TABLE_USER);
        // Creates fresh database
        this.onCreate(database);
    }

    public LogedInUser searchUser(String name, String email, String password) {
        LogedInUser logedInUser = null;

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_USER, new String [] { TABLE_USER_ID_FOR_TABLE, }, );


        database.close();
        return logedInUser;
    }
}
