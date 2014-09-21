package com.android.teamasia.miniyelp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by kienhoang on 9/20/14.
 */
public class CategoryTable {
    public static final String TABLE_NAME = "categories";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    private SQLiteDatabase database;
    private MiniYelpSQLiteHelper dbHelper;
    private String[] allColumns = {TABLE_NAME, COLUMN_ID, COLUMN_NAME};

    public CategoryTable(Context context) {
        dbHelper = new MiniYelpSQLiteHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Category createCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, category.getTitle());
        long catId = database.insert(TABLE_NAME, null, values);
        category.setId(catId);
        return category;
    }

}
