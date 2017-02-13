package com.tempcall.sajidhz.tempcall.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajidh on 2/7/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";
    // Database Info
    private static final String DATABASE_NAME = "TempContactDatabase";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String TABLE_USERCONTACTS = "usercontacts";


    //userdetail Table Columns
    private static final String _ID = "_id";
    private static final String NAME = "name";
    private static final String NUMBER = "number";

    private static DBHelper mDbHelper;


    public static synchronized DBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.

        if (mDbHelper == null) {
            mDbHelper = new DBHelper(context.getApplicationContext());
        }
        return mDbHelper;
    }


    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERDETAIL_TABLE = "CREATE TABLE " + TABLE_USERCONTACTS +
                "(" +
                _ID + " INTEGER PRIMARY KEY ," +
                NAME + " TEXT," +
                NUMBER + " TEXT" +
                ")";
        db.execSQL(CREATE_USERDETAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERCONTACTS);

            onCreate(db);
        }
    }

    public void insertUserDetail(UserContacts userContacts) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(NAME, userContacts.name);
            values.put(NUMBER, userContacts.number);

            db.insertOrThrow(TABLE_USERCONTACTS, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        } finally {

            db.endTransaction();
        }

    }

    public List<UserContacts> getAllUser() {

        List<UserContacts> usersdetail = new ArrayList<>();

        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_USERCONTACTS;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    UserContacts userData = new UserContacts();
                    userData.name = cursor.getString(cursor.getColumnIndex(NAME));
                    userData.number = cursor.getString(cursor.getColumnIndex(NUMBER));

                    usersdetail.add(userData);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return usersdetail;

    }

    void deleteRow(String name) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            db.execSQL("delete from " + TABLE_USERCONTACTS + " where name ='" + name + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Error while trying to delete  users detail");
        } finally {
            db.endTransaction();
        }


    }


}
