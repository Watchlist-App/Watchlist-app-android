package com.watchlist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.watchlist.authorization.LogedInUser;
import com.watchlist.authorization.LogedInUserContainer;

/**
 * Created by VEINHORN on 28/11/13.
 */

public class WatchListSQLiteOpenHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "WatchlistDB";
    public final static int DATABASE_VERSION = 1;

    // Commands that uses very often
    public final static String DATABASE_DROP_TABLE = "DROP TABLE IF EXISTS ";
    public final static String DATABASE_SELECT_ALL_FROM = "SELECT * FROM ";

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

    // Searches single user by email
    // This method returns object if it exists in database and null if not
    public LogedInUser searchUser(String mail) {
        LogedInUser logedInUser = null;
        LogedInUserContainer logedInUserContainer = getAllUsers();

        if(logedInUserContainer.getLogedInUserArrayList().isEmpty()) {
            return null;
        } else {
            logedInUser = logedInUserContainer.getLogedInUserArrayList().get(0);
        }

        return logedInUser;
    }

    // This method updates existing user in table
    // Must be  id primary key
    public int updateUser(LogedInUser logedInUser) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_USER_NAME, logedInUser.getName());
        contentValues.put(TABLE_USER_EMAIL, logedInUser.getEmail());
        contentValues.put(TABLE_USER_PASSWORD, logedInUser.getPassword());
        contentValues.put(TABLE_USER_DATE, logedInUser.getDate());

        // Updating row
        int informationMessage = database.update(TABLE_USER,
                contentValues,
                TABLE_USER_EMAIL + " = ?",
                new String[] {logedInUser.getEmail()});

        database.close();
        return informationMessage;
    }

    public void addUser(LogedInUser logedInUser) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_USER_ID_FOR_TABLE, logedInUser.getTableId());
        contentValues.put(TABLE_USER_NAME, logedInUser.getName());
        contentValues.put(TABLE_USER_EMAIL, logedInUser.getEmail());
        contentValues.put(TABLE_USER_PASSWORD, logedInUser.getPassword());
        contentValues.put(TABLE_USER_DATE, logedInUser.getDate());

        // Updating row
        database.insert(TABLE_USER,
                null,
                contentValues);
        database.close();
    }

    // This method for testing
    public LogedInUserContainer getAllUsers() {
        LogedInUserContainer logedInUserContainer = new LogedInUserContainer();
        // Build the query
        String query = DATABASE_SELECT_ALL_FROM + TABLE_USER;

        // Get reference to writable database
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        // Go over each row, build user item and it to UserContainer
        LogedInUser logedInUser = null;
        if(cursor.moveToFirst()) {
            do {
                logedInUser = new LogedInUser();
                logedInUser.setTableId(Integer.parseInt(cursor.getString(0)));
                logedInUser.setName(cursor.getString(1));
                logedInUser.setEmail(cursor.getString(2));
                logedInUser.setPassword(cursor.getString(3));
                logedInUser.setDate(cursor.getString(4));

                logedInUserContainer.getLogedInUserArrayList().add(logedInUser);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return logedInUserContainer;
    }

    // This is the method for testing
    public void deleteAllUsers() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_USER, null, null);

        database.close();
    }

    // This is the method for testing
    public void clearAndUpdateDatabase() {
        SQLiteDatabase database = this.getWritableDatabase();
        deleteAllUsers();
        // Drop older table if exists
        database.execSQL(DATABASE_DROP_TABLE + TABLE_USER);
        // Creates fresh database
        this.onCreate(database);
    }

    public boolean isUserTableIsEmpty() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_USER, null);

        if(cursor.getCount() > 0) {
            cursor.close();
            database.close();
            return false;
        }
        cursor.close();
        database.close();
        return true;
    }
}
