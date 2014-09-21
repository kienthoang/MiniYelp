package com.android.teamasia.miniyelp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by kienhoang on 9/20/14.
 */
public class ReviewerTable {
    public static final String TABLE_NAME = "reviewers";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    private SQLiteDatabase database;
    private MiniYelpSQLiteHelper dbHelper;
    private String[] allColumns = {TABLE_NAME, COLUMN_ID, COLUMN_NAME};

    public ReviewerTable(Context context) {
        dbHelper = new MiniYelpSQLiteHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Reviewer createReviewer(Reviewer reviewer) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, reviewer.getName());
        long reviewerId = database.insert(TABLE_NAME, null, values);
        reviewer.setId(reviewerId);
        return reviewer;
    }
}
