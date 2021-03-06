package com.android.teamasia.miniyelp;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;

import com.android.teamasia.miniyelp.database.MiniYelpQueryHandler;

import java.util.ArrayList;
import java.util.List;


public class ResultsActivity extends ListActivity {

    public static final String EXTRA_CITY = "com.android.teamasia.miniyelp.city";
    public static final String EXTRA_CAT_ARR = "com.android.teamasia.miniyelp.cat_arr";
    public static final String EXTRA_COST = "com.android.teamasia.miniyelp.cost";
    public static final String EXTRA_DAY = "com.android.teamasia.miniyelp.day";
    public static final String EXTRA_TIME = "com.android.teamasia.miniyelp.hour";
    public static final String EXTRA_SEARCH_BY_TIME = "com.android.teamasia.miniyelp.search_time";

    private String city;
    private String[] cat_arr;
    private int cost;
    private String day;
    private int time;
    private boolean searchByTime;

    /**
     * Creates the ResultsActivity
     * @param savedInstanceState Previously saved instance of this activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // get user input from SearchActivity
        city = getIntent().getStringExtra(EXTRA_CITY);
        cat_arr = getIntent().getStringArrayExtra(EXTRA_CAT_ARR);
        cost = getIntent().getIntExtra(EXTRA_COST, -1);
        day = getIntent().getStringExtra(EXTRA_DAY);
        time = getIntent().getIntExtra(EXTRA_TIME, -1);
        searchByTime = getIntent().getBooleanExtra(EXTRA_SEARCH_BY_TIME, false);

        // instantiate qury handler
        MiniYelpQueryHandler myqh = new MiniYelpQueryHandler(this);

        // list of returned restaurants
        List<String> results = myqh.startQuery(city, cat_arr, cost, day, time);
        Log.d("Result list size: ", Integer.toString(results.size()));

        // add results to ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, results);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * Creates options menu
     * @param menu
     * @return whether menu was created or not
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results, menu);
        return true;
    }

    /**
     * Handles selection of items from options menu
     * @param item
     * @return whether option selection was successful or not
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
