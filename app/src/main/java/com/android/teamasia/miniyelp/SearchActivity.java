package com.android.teamasia.miniyelp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

import android.util.Log;

import com.android.teamasia.miniyelp.database.Category;
import com.android.teamasia.miniyelp.database.CategoryTable;
import com.android.teamasia.miniyelp.database.MiniYelpQueryHandler;
import com.android.teamasia.miniyelp.database.MiniYelpSQLiteHelper;
import com.android.teamasia.miniyelp.database.Restaurant;
import com.android.teamasia.miniyelp.database.RestaurantTable;
import com.android.teamasia.miniyelp.database.RestaurantTime;
import com.android.teamasia.miniyelp.database.RestaurantTimesTable;
import com.android.teamasia.miniyelp.database.MiniYelpSQLiteHelper;
import com.android.teamasia.miniyelp.database.RestaurantsCategoriesTable;


public class SearchActivity extends ActionBarActivity {

    private Context context;
    private List<EditText> categoryList = new ArrayList<EditText>();
    private String timeDay;
    private boolean searchByTime;

    /**
     * Creates the SearchActivity
     * @param savedInstanceState Previously saved instance of this activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // disable soft keyboard opening up for first TextView on launch
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // 24-hour TimePicker
        final TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);

        // instantiate our SQLiteHelper
        MiniYelpSQLiteHelper miniyelp = new MiniYelpSQLiteHelper(this);
        Log.d("size", miniyelp.getDatabaseSize() + "");

        // read input file
        try{
            RestaurantTable rt = new RestaurantTable(this);
            rt.open();
            List<Restaurant> rl = rt.getAllRestaurants();
            if(rl.size() < 1){
                InputParser once = new InputParser(this);
                once.parseInputBlock("InputFile");
            }
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }

        // checkbox to enable/disable searching by time
        final CheckBox checkBox = (CheckBox) findViewById(R.id.time_picker_checkBox);
        checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton c, boolean b) {
                if(checkBox.isChecked()) {
                    timePicker.setVisibility(View.VISIBLE);
                    searchByTime = true;
                }
                else {
                    timePicker.setVisibility(View.INVISIBLE);
                    searchByTime = false;
                }
            }
        });

        // application context
        context = this.getApplicationContext();

        // Spinner to select day of week
        Spinner spinner = (Spinner) findViewById(R.id.daySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.day_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                timeDay = parent.getItemAtPosition(pos).toString().toLowerCase();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                timeDay = "";
            }
        });


        // add the first item into the category list
        categoryList.add((EditText) findViewById(R.id.category_item));
        Button addButton =(Button) findViewById(R.id.add_button);

        // add new lines for multiple categories
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newCategory = new EditText(SearchActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                newCategory.setLayoutParams(params);
                LinearLayout categories = (LinearLayout) findViewById(R.id.category_list);
                categories.addView(newCategory);
                categoryList.add(newCategory);
            }
        });

        // remove excessive lines of category
        Button removeButton = (Button) findViewById(R.id.remove_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryList.size() > 1) {
                    LinearLayout categories = (LinearLayout) findViewById(R.id.category_list);
                    EditText deleteCategory = categoryList.get(categoryList.size() - 1);
                    categories.removeView(deleteCategory);
                    categoryList.remove(categoryList.size() - 1);
                }
            }
        });

        // search for restaurants
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get all user input
                String cityName = ((EditText) findViewById(R.id.cityName)).getText().toString().toLowerCase();
                String[] catArr = new String[categoryList.size()];
                for (int i = 0; i < catArr.length; i++) {
                    catArr[i] = categoryList.get(i).getText().toString().toLowerCase();
                }
                int cost = (int) ((RatingBar) findViewById(R.id.ratingBar)).getRating();
                int hour = ((TimePicker) findViewById(R.id.time_picker)).getCurrentHour();
                int minute = ((TimePicker) findViewById(R.id.time_picker)).getCurrentMinute();
                int time;
                if(searchByTime) {
                    time = hour * 100 + minute;
                } else {
                    time = -1;
                }
                // pass to ResultsActivity
                Intent i = new Intent(SearchActivity.this, ResultsActivity.class);
                i.putExtra(ResultsActivity.EXTRA_CITY, cityName);
                i.putExtra(ResultsActivity.EXTRA_CAT_ARR, catArr);
                i.putExtra(ResultsActivity.EXTRA_COST, cost);
                i.putExtra(ResultsActivity.EXTRA_DAY, timeDay);
                i.putExtra(ResultsActivity.EXTRA_TIME, time);
                i.putExtra(ResultsActivity.EXTRA_SEARCH_BY_TIME, searchByTime);
                startActivity(i);
            }
        });
    }

    /**
     * Creates options menu
     * @param menu
     * @return whether menu was created or not
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
