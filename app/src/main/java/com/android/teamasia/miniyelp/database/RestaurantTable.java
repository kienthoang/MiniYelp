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
}
