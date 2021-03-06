package com.android.teamasia.miniyelp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.*;

/**
 * Created by Yiming Chen on 9/22/2014.
 */

public class MiniYelpQueryHandler {
    private SQLiteDatabase database;
    private MiniYelpSQLiteHelper helper;

    /**
     * Constructor
     * @param context The application context
     */
    public MiniYelpQueryHandler(Context context) {
        helper = new MiniYelpSQLiteHelper(context);
        database = helper.getReadableDatabase();
    }

    /**
     * Starts a query
     * @param cityName City
     * @param catArr List of categories
     * @param cost Cost
     * @param day Day of week
     * @param time Time
     * @return List of results
     */
    public List<String> startQuery(String cityName, String[]catArr, int cost, String day, int time) {

        Log.d("check param", cityName + "\n" + Arrays.toString(catArr) + "\n" + cost + "\n" + day + "^\n" + time);

        // query builder
        SQLiteQueryBuilder mainBuilder = new SQLiteQueryBuilder();
        mainBuilder.setTables(RestaurantTable.TABLE_NAME);

        // Add the WHERE clauses for the cost and city
        List<String> clauses = new ArrayList<String>();
        if (cost > 0) {
            clauses.add(RestaurantTable.COLUMN_COST + " = " + cost);
        }

        if (!cityName.equals("")) {
            clauses.add(RestaurantTable.COLUMN_CITY + " = '" + cityName + "'");
        }

        for (int i = 0; i < clauses.size(); i++) {
            String clause = clauses.get(i);
            if (i < clauses.size() - 1) {
                clause += " AND ";
            }
            mainBuilder.appendWhere(clause);
        }

        // build query
        String restaurantsQuery = mainBuilder.buildQuery(
                new String[]{"*"},
                null, null, null, null, null, null);
        restaurantsQuery = "(" + restaurantsQuery + ") AS T1";

        // Queries for categories that match the search.
        SQLiteQueryBuilder catBuilder = new SQLiteQueryBuilder();
        catBuilder.setTables(RestaurantsCategoriesTable.TABLE_NAME + " JOIN " +
                             CategoryTable.TABLE_NAME + " ON " +
                             CategoryTable.COLUMN_ID + " = " +
                             RestaurantsCategoriesTable.COLUMN_CATEGORY_ID);

        for (int i = 0; i < catArr.length; i++) {
            String category = catArr[i];
            if (category.equals("")) {
                continue;
            }

            String whereClause = CategoryTable.COLUMN_NAME + " = '" + catArr[i] + "'";
            if (i < catArr.length - 1) {
                whereClause += " OR ";
            }
            catBuilder.appendWhere(whereClause);
        }

        String categoryQuery = catBuilder.buildQuery(
                new String[]{RestaurantsCategoriesTable.COLUMN_CATEGORY_ID,
                             RestaurantsCategoriesTable.COLUMN_RESTAURANT_ID,
                             CategoryTable.COLUMN_NAME  + " AS category_type "},
                null, null, null, null, null, null);
        categoryQuery = "(" + categoryQuery + ") AS T2";

        // Queries for the restaurant times that match the search.
        SQLiteQueryBuilder timeBuilder = new SQLiteQueryBuilder();
        timeBuilder.setTables(RestaurantTimesTable.TABLE_NAME);
        List<String> timeWhereClauses = new ArrayList<String>();
        if (!day.equals("")) {
            timeWhereClauses.add(RestaurantTimesTable.COLUMN_DAY + " = '" + day + "'");
        }
        if (time > 0) {
            timeWhereClauses.add(RestaurantTimesTable.COLUMN_START_TIME + " <= " + time);
            timeWhereClauses.add(RestaurantTimesTable.COLUMN_END_TIME + " >= " + time);
        }
        for (int i = 0; i < timeWhereClauses.size(); i++) {
            String whereClause = timeWhereClauses.get(i);
            if (i < timeWhereClauses.size() - 1) {
                whereClause += " AND ";
            }
            timeBuilder.appendWhere(whereClause);
        }
        String timeQuery = timeBuilder.buildQuery(
                new String[]{RestaurantTimesTable.COLUMN_DAY,
                        RestaurantTimesTable.COLUMN_START_TIME,
                        RestaurantTimesTable.COLUMN_END_TIME,
                        RestaurantTimesTable.COLUMN_RESTAURANT_ID}, null, null, null, null, null, null);
        timeQuery = "(" + timeQuery + ") AS T3";

        // Join the queries for the restaurants and categories to build the main query.
        String query = restaurantsQuery + " INNER JOIN " + categoryQuery + " ON T1._id = T2.restaurant_id";
        query = "SELECT " + RestaurantTable.COLUMN_NAME + ", " +
                 RestaurantTable.COLUMN_STREET + ", " + RestaurantTable.COLUMN_CITY + ", " +
                 RestaurantTable.COLUMN_COST + ", " + RestaurantTable.COLUMN_RANK + ", " +
                 RestaurantTable.COLUMN_REVIEWERS + ", " +
//"Group_Concat(DISTINCT " + RestaurantTimesTable.COLUMN_DAY + ") AS day, " +
//"Group_Concat(DISTINCT " + RestaurantTimesTable.COLUMN_START_TIME + ") AS opening_time, " +
//"Group_Concat(DISTINCT " + RestaurantTimesTable.COLUMN_END_TIME + ") AS closing_time, " +
                "Group_Concat(DISTINCT coalesce(" + RestaurantTimesTable.COLUMN_DAY + ", ' ') || ' '|| " +
                "coalesce(" + RestaurantTimesTable.COLUMN_START_TIME + ", ' ') || ' ' || " +
                "coalesce(" + RestaurantTimesTable.COLUMN_END_TIME + ", ' ')) AS open_close_hour, " +
                "Group_Concat(DISTINCT category_type) AS category_type"
              + " FROM (" + query + ") AS T12";
        query += " INNER JOIN " + timeQuery + " ON T12._id = T3.restaurant_id " +
                "GROUP BY _id ORDER BY " + RestaurantTable.COLUMN_CITY + " ASC, " +
                RestaurantTable.COLUMN_RANK + " DESC, " + RestaurantTable.COLUMN_COST + " ASC" ;
        Log.d("test cat 2", query);

        // Result parsing.
        List<String> results = new ArrayList<String>();
        try {
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();

            String[] columnTitles = cursor.getColumnNames();

            while (!cursor.isAfterLast()) {

                String result = "";
                for (int i = 0; i < columnTitles.length - 1; i++) {
                    result += columnTitles[i] + ": " + cursor.getString(i) + "\n";
                }
                result += columnTitles[columnTitles.length - 1] + ": " +
                          cursor.getString(columnTitles.length - 1);
                Log.d("check result", result);
                results.add(result);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    // Test method for query, not used in actual app
    private void printRestaurantCategoriesTable() {
        String queryString = "SELECT * FROM ((" + RestaurantTable.TABLE_NAME + " JOIN " +
                RestaurantsCategoriesTable.TABLE_NAME + " ON " + RestaurantTable.COLUMN_ID +
                " = " + RestaurantsCategoriesTable.COLUMN_RESTAURANT_ID + ") AS T1";
        queryString += " JOIN " +
                CategoryTable.TABLE_NAME + " ON " + RestaurantsCategoriesTable.COLUMN_CATEGORY_ID +
                " = " + CategoryTable.TABLE_NAME + "." + CategoryTable.COLUMN_ID + ")";

        List<String> results = new ArrayList<String>();
        try {
            Cursor cursor = database.rawQuery(queryString, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                String[] strArr = cursor.getColumnNames();
                //Log.d("Col name", Arrays.toString(strArr));
                String result = "";
                for (int i = 0; i < strArr.length; i++) {
                    result += cursor.getString(i) + " ";
                }
                Log.d("check table", result);
                results.add(result);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        };

    }
}
