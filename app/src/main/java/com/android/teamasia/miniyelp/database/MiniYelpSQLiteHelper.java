package com.android.teamasia.miniyelp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kienhoang on 9/20/14.
 */
public class MiniYelpSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "miniyelp.db";
    private static final int DATABASE_VERSION = 1;

    public MiniYelpSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the restaurant table.
     * @param database The database.
     */
    public void createRestaurantTable(SQLiteDatabase database) {
        database.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, " +
                                       "%s INTEGER, %s INTEGER, %s TEXT, %s TEXT, %s INTEGER)",
                RestaurantTable.TABLE_NAME, RestaurantTable.COLUMN_ID,
                RestaurantTable.COLUMN_NAME, RestaurantTable.COLUMN_RANK,
                RestaurantTable.COLUMN_COST, RestaurantTable.COLUMN_STREET,
                RestaurantTable.COLUMN_CITY, RestaurantTable.COLUMN_REVIEWERS));
    }

    /**
     * Creates the restaurant-category table.
     * @param database The database.
     */
    public void createRestaurantsCategoriesTable(SQLiteDatabase database) {
        database.execSQL(String.format("CREATE TABLE %s (%s INTEGER, %s INTEGER, " +
                        "FOREIGN KEY(%s) REFERENCES %s(%s), " +
                        "FOREIGN KEY(%s) REFERENCES %s(%s))",
                RestaurantsCategoriesTable.TABLE_NAME,

                RestaurantsCategoriesTable.COLUMN_CATEGORY_ID,
                RestaurantsCategoriesTable.COLUMN_RESTAURANT_ID,

                RestaurantsCategoriesTable.COLUMN_CATEGORY_ID,
                CategoryTable.TABLE_NAME,
                CategoryTable.COLUMN_ID,

                RestaurantsCategoriesTable.COLUMN_RESTAURANT_ID,
                RestaurantTable.TABLE_NAME,
                RestaurantTable.COLUMN_ID));
    }

    /**
     * Creates the category table.
     * @param database The database.
     */
    public void createCategoryTable(SQLiteDatabase database) {
        database.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, " +
                        "%s TEXT, UNIQUE(%s))",
                CategoryTable.TABLE_NAME,
                CategoryTable.COLUMN_ID, CategoryTable.COLUMN_NAME,
                /*CategoryTable.COLUMN_ID,*/ CategoryTable.COLUMN_NAME));
    }

    /**
     * Creates the restaurant times table.
     * @param database The database.
     */
    public void createRestaurantTimesTable(SQLiteDatabase database) {
        database.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, " +
                        "%s INTEGER, %s INTEGER, %s INTEGER, "+
                        "FOREIGN KEY(%s) REFERENCES %s(%s))",
                RestaurantTimesTable.TABLE_NAME, RestaurantTimesTable.COLUMN_ID,
                RestaurantTimesTable.COLUMN_DAY, RestaurantTimesTable.COLUMN_START_TIME,
                RestaurantTimesTable.COLUMN_END_TIME,
                RestaurantTimesTable.COLUMN_RESTAURANT_ID,

                RestaurantTimesTable.COLUMN_RESTAURANT_ID,
                RestaurantTable.TABLE_NAME,
                RestaurantTable.COLUMN_ID));
    }

    /**
     * Returns database size
     * @return Size of database
     */
     public long getDatabaseSize(){
         long size = getReadableDatabase().getPageSize();
         close();
         return size;
    }

    /**
     * Creates database tables
     * @param database Database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        createRestaurantTable(database);
        createRestaurantsCategoriesTable(database);
        createCategoryTable(database);
        createRestaurantTimesTable(database);
        //createReviewTable(database);
        //createReviewerTable(database);
    }

    /**
     * Upgrades database to new version
     * @param db Database
     * @param oldVersion Old version
     * @param newVersion New version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RestaurantTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RestaurantsCategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CategoryTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RestaurantTimesTable.TABLE_NAME);
        onCreate(db);
    }
}
