package com.android.teamasia.miniyelp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

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

        boolean catJoined = false;
        boolean timeJoined = false;

        boolean noMainClause = true;

        SQLiteQueryBuilder mainBuilder = new SQLiteQueryBuilder();
        mainBuilder.setTables(RestaurantTable.TABLE_NAME);

        if (!cityName.equals("")) {
            mainBuilder.appendWhere(RestaurantTable.COLUMN_CITY + " = '" + cityName + "'");
            noMainClause = false;
        }
        if (catArr.length > 0) {
            catJoined = true;
        }
        if (cityName.equals("") && cost > 0) {
            mainBuilder.appendWhere(RestaurantTable.COLUMN_COST + "=" + cost);
            noMainClause = false;
        } else if (cost > 0) {
            mainBuilder.appendWhere(" AND " + RestaurantTable.COLUMN_COST + " = " + cost);
        }

        String str = mainBuilder.buildQuery(
                new String[]{"*"},
                null, null, null, null, null, null);
        str = "(" + str + ")";
        Log.d("test q", str);

//        if (catJoined) {
            SQLiteQueryBuilder catBuilder = new SQLiteQueryBuilder();
            catBuilder.setTables(RestaurantsCategoriesTable.TABLE_NAME + " JOIN " +
                                 CategoryTable.TABLE_NAME + " ON " +
                                 CategoryTable.COLUMN_ID + " = " +
                                 RestaurantsCategoriesTable.COLUMN_CATEGORY_ID);
            if (!catArr[0].equals("")) {
                catBuilder.appendWhere(CategoryTable.COLUMN_NAME + " = '" + catArr[0] + "'");
            }
            for (int i = 1; i < catArr.length; i++) {
                catBuilder.appendWhere(" AND " + CategoryTable.COLUMN_NAME + " = '" + catArr[i] + "'");
            }
            String str2 = catBuilder.buildQuery(
                    new String[]{"*"},
                    null, null, null, null, null, null);
            str2 = "(" + str2 + ")";
            Log.d("test cat", str2);
            str += " AS T1";
            str2 += " AS T2";
            str = str + " INNER JOIN " + str2 + " ON T1._id = T2.restaurant_id";
            str = "SELECT * FROM " + str + " GROUP BY _id";
            Log.d("test cat 2", str);
//        }
        if (timeJoined) {


        }
        try {
            Cursor cursor = database.rawQuery(str, null);
            //Cursor cursor = database.rawQuery("SELECT * " + " FROM " + RestaurantTable.TABLE_NAME + " WHERE (" + RestaurantTable.COLUMN_CITY + " = '" + cityName + "')", null);
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
//        if (catJoined)

//        if (!day.equals("") || time > 0) {
//            timeJoined = true;
//        }
    }
}
