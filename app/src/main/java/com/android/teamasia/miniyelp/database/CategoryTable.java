package com.android.teamasia.miniyelp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kienhoang on 9/20/14.
 */
public class CategoryTable {
    public static final String TABLE_NAME = "categories";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    private SQLiteDatabase database;
    private MiniYelpSQLiteHelper dbHelper;
    private String[] allColumns = {COLUMN_ID, COLUMN_NAME};

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
        try {
            long catId = database.insertOrThrow(TABLE_NAME, null, values);
            category.setId(catId);
        } catch (android.database.sqlite.SQLiteConstraintException e) {
            SQLiteQueryBuilder catBuilder = new SQLiteQueryBuilder();
            catBuilder.setTables(TABLE_NAME);
            catBuilder.appendWhere(COLUMN_NAME + " = '" + category.getTitle() + "'");
            String query = catBuilder.buildQuery(new String[]{"*"}, null, null, null, null, null, null);
            query = "SELECT _id FROM (" + query + ")";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            category.setId(cursor.getLong(0));
        }
        return category;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<Category>();
        Cursor cursor = database.query(TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Category cat = cursorToCategories(cursor);
            categories.add(cat);
            cursor.moveToNext();
        }

        cursor.close();
        return categories;
    }

    private Category cursorToCategories(Cursor cursor) {
        Category cat = new Category("");
        cat.setId(cursor.getLong(0));
        cat.setTitle(cursor.getString(1));
        return cat;
    }
}
