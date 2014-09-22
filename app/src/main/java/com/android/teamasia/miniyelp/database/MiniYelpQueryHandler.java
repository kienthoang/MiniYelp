package com.android.teamasia.miniyelp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.*;

/**
 * Created by DELL on 9/22/2014.
 */
public class MiniYelpQueryHandler {
    private SQLiteDatabase database;
    private MiniYelpSQLiteHelper helper;

    public MiniYelpQueryHandler(Context context) {
        helper = new MiniYelpSQLiteHelper(context);
        database = helper.getReadableDatabase();
    }

    public List<String> startQuery(String cityName, String[]catArr, int cost, String day, int time) {

        Log.d("cehck param", cityName + "\n" + Arrays.toString(catArr) + "\n" + cost + "\n" + day + "^\n" + time);

        SQLiteQueryBuilder mainBuilder = new SQLiteQueryBuilder();
        mainBuilder.setTables(RestaurantTable.TABLE_NAME);

        // Add the WHERE clauses for the cost and city;
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
                new String[]{"*"},
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
                new String[]{"*"}, null, null, null, null, null, null);
        timeQuery = "(" + timeQuery + ") AS T3";

        // Join the queries for the restaurants and categories to build the main query.
        String query = restaurantsQuery + " INNER JOIN " + categoryQuery + " ON T1._id = T2.restaurant_id";
        query = "SELECT * FROM (" + query + ") AS T12";
        query += " INNER JOIN " + timeQuery + " ON T12._id = T3.restaurant_id GROUP BY _id";
        Log.d("test cat 2", query);

        // Result parsing.
        List<String> results = new ArrayList<String>();
        try {
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                String[] strArr = cursor.getColumnNames();
                String result = "";
                for (int i = 0; i < strArr.length; i++) {
                    result += cursor.getString(i) + " ";
                }
                Log.d("check result", result);
                results.add(result);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!catArr[0].equals("")) {
            printRestaurantCategoriesTable();
        }

        return results;
    }

    public void printRestaurantCategoriesTable() {
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
