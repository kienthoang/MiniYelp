package com.android.teamasia.miniyelp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kienhoang on 9/20/14.
 */
public class RestaurantTimesTable {
    public static final String TABLE_NAME = "restaurant_times";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_RESTAURANT_ID = "restaurant_id";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";

    private SQLiteDatabase database;
    private MiniYelpSQLiteHelper dbHelper;

    public RestaurantTimesTable(Context context) {
        dbHelper = new MiniYelpSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public RestaurantTime createRestaurantTime(RestaurantTime restaurantTime) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY, restaurantTime.getDay());
        values.put(COLUMN_START_TIME, restaurantTime.getStartTime());
        values.put(COLUMN_END_TIME, restaurantTime.getEndTime());
        values.put(COLUMN_RESTAURANT_ID, restaurantTime.getRestaurantId());
        database.insert(TABLE_NAME, null, values);
        return restaurantTime;
    }
}
