package com.android.teamasia.miniyelp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

/**
 * Created by DELL on 9/22/2014.
 */
public class MiniYelpQueryHandler {
    private SQLiteDatabase database;
    private MiniYelpSQLiteHelper helper;
    private SQLiteQueryBuilder builder;

    public MiniYelpQueryHandler(Context context) {
        helper = new MiniYelpSQLiteHelper(context);
        database = helper.getReadableDatabase();
        builder = new SQLiteQueryBuilder();
//        builder.setTables(RestaurantTable.TABLE_NAME);
    }

    public void startQuery(String cityName, String[]catArr, int cost, String day, int time) {

        boolean catJoined = false;
        boolean timeJoined = false;

        builder.setTables(RestaurantTable.TABLE_NAME);

        if (!cityName.equals("")) {
            builder.appendWhere(RestaurantTable.COLUMN_CITY + "=" + cityName);
        }
        if (catArr.length > 0) {
            catJoined = true;
        }
        if (cost > 0) {
            builder.appendWhere(RestaurantTable.COLUMN_COST + "=" + cost);
        }

        String str = builder.buildQuery(new String[]{RestaurantTable.COLUMN_ID}, null, null, null, null, null, null);
        Log.d("test q", str);

        if (catJoined) {

        }

//        if (!day.equals("") || time > 0) {
//            timeJoined = true;
//        }
    }
}
