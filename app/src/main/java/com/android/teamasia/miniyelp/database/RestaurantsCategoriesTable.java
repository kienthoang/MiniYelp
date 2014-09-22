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
public class RestaurantsCategoriesTable {
    public static final String TABLE_NAME = "restaurants_categories";
    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_RESTAURANT_ID = "restaurant_id";

    private SQLiteDatabase database;
    private MiniYelpSQLiteHelper dbHelper;

    public RestaurantsCategoriesTable(Context context) {
        dbHelper = new MiniYelpSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public RestaurantsCategories createRestaurantsCategories(RestaurantsCategories restaurantsCategories) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_ID, restaurantsCategories.getCategoryId());
        values.put(COLUMN_RESTAURANT_ID, restaurantsCategories.getRestaurantId());
        database.insert(TABLE_NAME, null, values);
        return restaurantsCategories;
    }
}
