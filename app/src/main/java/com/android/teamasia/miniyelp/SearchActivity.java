package com.android.teamasia.miniyelp;

import android.content.Context;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import com.android.teamasia.miniyelp.database.CategoryTable;
import com.android.teamasia.miniyelp.database.Restaurant;
import com.android.teamasia.miniyelp.database.RestaurantTable;
import com.android.teamasia.miniyelp.database.RestaurantTime;
import com.android.teamasia.miniyelp.database.RestaurantTimesTable;
import com.android.teamasia.miniyelp.database.RestaurantsCategoriesTable;


public class SearchActivity extends ActionBarActivity {

    private Context context;
    private List<EditText> categoryList = new ArrayList<EditText>();
    private String timeDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);

        context = this.getApplicationContext();

        // spinner
        Spinner spinner = (Spinner) findViewById(R.id.daySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.day_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                timeDay = parent.getItemAtPosition(pos).toString();
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
        Button removeButton = (Button) findViewById(R.id.remove_button);
        // remove excessive lines of category.
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
    }

    public void startQuery(View view) {
        String cityName = ((EditText) findViewById(R.id.cityName)).getText().toString();
        String[] catArr = new String[categoryList.size()];
        for (int i = 0; i < catArr.length; i++) {
            catArr[i] = categoryList.get(i).getText().toString();
        }
        int cost = (int) ((RatingBar) findViewById(R.id.ratingBar)).getRating();
        int hour = ((TimePicker) findViewById(R.id.time_picker)).getCurrentHour();
        int minute = ((TimePicker) findViewById(R.id.time_picker)).getCurrentMinute();
        Log.d("user input test", cityName + "\n" + Arrays.toString(catArr) + "\n"
        + cost + "\n" + timeDay + ", " + hour + ":" + minute);

        testTable(cityName, catArr, cost, timeDay, hour*100 + minute);
    }

    private void testTable(String cityName, String[]catArr, int cost, String day, int time) {

        Restaurant res = new Restaurant("", cityName, 1, cost, "res1");
        Restaurant res2 = new Restaurant("", cityName, 2, cost + 1, "res2");

        try {
//        RestaurantTime rt = new RestaurantTime();
//        rt.setDay(day);
//        rt.setStartTime(time);
//        rt.setEndTime(time + 100);
            RestaurantTable rtb = new RestaurantTable(this);
            rtb.open();
            rtb.createRestaurant(res);
            rtb.createRestaurant(res2);
            rtb.close();
            List<Restaurant> list = rtb.getAllRestaurants();
            Log.d("test Res1", list.get(0).getId() + "," + list.get(0).getName() + ", "
                    + list.get(0).getCity() + ", " + list.get(0).getCost() + "\n");
            Log.d("test Res2", list.get(1).getId() + "," + list.get(1).getName() + ", "
                    + list.get(1).getCity() + ", " + list.get(1).getCost());
        } catch (Exception e) {
            Log.e("test Res error", e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

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
