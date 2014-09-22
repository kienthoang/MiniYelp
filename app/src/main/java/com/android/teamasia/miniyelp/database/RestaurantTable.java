package com.android.teamasia.miniyelp.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by kienhoang on 9/20/14.
 */
public class RestaurantTable {
    public static final String TABLE_NAME = "restaurants";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_RANK = "rank";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_STREET = "street";
    public static final String COLUMN_CITY = "city";

    private SQLiteDatabase database;
    private MiniYelpSQLiteHelper dbHelper;
    private String[] allColumns = {COLUMN_ID, COLUMN_NAME, COLUMN_RANK,
                                   COLUMN_COST, COLUMN_STREET, COLUMN_CITY};

    public RestaurantTable(Context context) {
        dbHelper = new MiniYelpSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, restaurant.getName());
        values.put(COLUMN_RANK, restaurant.getRank());
        values.put(COLUMN_COST, restaurant.getCost());
        values.put(COLUMN_STREET, restaurant.getStreet());
        values.put(COLUMN_CITY, restaurant.getCity());
        long restaurantId = database.insert(TABLE_NAME, null, values);
        restaurant.setId(restaurantId);
        return restaurant;
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        Cursor cursor = database.query(TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Restaurant restaurant = cursorToRestaurant(cursor);
            restaurants.add(restaurant);
            cursor.moveToNext();
        }

        cursor.close();
        return restaurants;
    }


    private Restaurant cursorToRestaurant(Cursor cursor) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(cursor.getLong(0));
        restaurant.setName(cursor.getString(1));
        restaurant.setRank(cursor.getInt(2));
        restaurant.setCost(cursor.getInt(3));
        restaurant.setStreet(cursor.getString(4));
        restaurant.setCity(cursor.getString(5));
        return restaurant;
    }
}
