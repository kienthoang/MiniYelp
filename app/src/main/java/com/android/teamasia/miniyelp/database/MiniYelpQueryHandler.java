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
//        builder.setTables(RestaurantTable.TABLE_NAME);
    }

    public void startQuery(String cityName, String[]catArr, int cost, String day, int time) {
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


        // Join the queries for the restaurants and categories to build the main query.
        String query = "SELECT * FROM " +
                restaurantsQuery + " INNER JOIN " + categoryQuery + " ON T1._id = T2.restaurant_id" +
                " GROUP BY _id";
        Log.d("test cat 2", query);

        // TODO(kienhoang): Parse the results into a list of Restaurant objects.
        try {
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();

            String outputStr = "";

            while (!cursor.isAfterLast()) {
                String[] strArr = cursor.getColumnNames();
                for (int i = 0; i < strArr.length; i++) {
                    outputStr += cursor.getString(i) + " ";
                }
                outputStr += "\n";
                cursor.moveToNext();
            }
            cursor.close();
            Log.d("...", outputStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
