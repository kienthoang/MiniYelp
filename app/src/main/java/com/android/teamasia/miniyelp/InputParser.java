package com.android.teamasia.miniyelp;

import android.content.Context;
import android.content.res.AssetManager;


import com.android.teamasia.miniyelp.database.Category;
import com.android.teamasia.miniyelp.database.CategoryTable;
import com.android.teamasia.miniyelp.database.Restaurant;
import com.android.teamasia.miniyelp.database.RestaurantTable;
import com.android.teamasia.miniyelp.database.RestaurantTime;
import com.android.teamasia.miniyelp.database.RestaurantTimesTable;
import com.android.teamasia.miniyelp.database.RestaurantsCategories;
import com.android.teamasia.miniyelp.database.RestaurantsCategoriesTable;

import  java.util.*;
import java.io.*;
import java.lang.Object;
/**
 * Created by kienhoang on 9/20/14.
 * Modified by bikram on 9/20/14.
 */
public class InputParser {

    private static Context context;

    public static void main(String args[]){
        parseInputBlock("InputFile.txt");
    }

    public InputParser(Context context) {
        this.context = context;
    }

    public static void parseInputBlock(String block)  {

        try{
            AssetManager am = context.getAssets();
            InputStream is = am.open(block);
//            File inputFile = new File(block);
            Scanner sc = new Scanner(is);
            // variables
            double rank =0;
            int cost = 0 ;
            ArrayList<String> opening = new ArrayList<String>();
            String name= "";
            String street=  "";
            ArrayList<String> category = new ArrayList<String>();
            String reviewers="";
            String city= "";

            //database table
            CategoryTable categorytable = new CategoryTable(context);
            RestaurantTable resturanttable = new RestaurantTable(context);
            RestaurantTimesTable resturanttimetable = new RestaurantTimesTable(context);
            RestaurantsCategoriesTable rescattable = new RestaurantsCategoriesTable(context);


            // check for the end of the text file
            while(sc.hasNext()){

                String currentline = sc.nextLine();


                if(!currentline.equals("")){
                    String variable = currentline.substring(0,2);

                    //rank

                    if(variable.equals("ra")){
                        rank =  Double.parseDouble(currentline.substring(currentline.indexOf(" ")+1, currentline.length()));

                    }
                    //cost
                    else if(variable.equals("co")){
                        //cost =  Integer.currentline.substring(currentline.indexOf(" ")+1, currentline.length());
                        cost = count(currentline);
                    }

                    // opening hours
                    else if(variable.equals("op")){
                        String openinglist =  currentline.substring(currentline.indexOf(" ")+1, currentline.length());
                        String[] openinglist1 = openinglist.split(",");
                        opening = convert(openinglist1);

                    }

                    // address aka street
                    else if(variable.equals("st")){
                        street =  currentline.substring(currentline.indexOf(" ")+1, currentline.length());
                    }

                    //name
                    else if(variable.equals("na")){
                        name =  currentline.substring(currentline.indexOf(" ")+1, currentline.length());
                    }

                    // category
                    else if(variable.equals("ca")){
                        String first = currentline.substring(currentline.indexOf(" ")+1, currentline.length());
                        String[] category1 = first.split(",");
                        category = convert(category1);
                    }

                    // reviewers
                    else if(variable.equals("re")){
                        reviewers =  currentline.substring(currentline.indexOf(" ")+1, currentline.length());
                    }

                    //city
                    else if(variable.equals("ci")){
                        city =  currentline.substring(currentline.indexOf(" ")+1, currentline.length());
                    }
                }
                else{
                    //create a new resturant
                    resturanttable.open();
                    Restaurant temp = new Restaurant(street, city, rank, cost, name);
                    ////System.out.println("street: "+street + "  city:  " + city +  "  rank:   " + rank + "   cost:   " + cost + " name:   " + name);

                    //add resutrant to resturantable
                    Restaurant add  = resturanttable.createRestaurant(temp);
                    resturanttable.close();
                    // add all category to category table and also add to resturant-category table
                    categorytable.open();
                    rescattable.open();
                    for(String s: category){
                        Category temp2 = new Category(s);
                        Category temp1 = categorytable.createCategory(temp2);
                        RestaurantsCategories restcat = new RestaurantsCategories(add.getId(), temp1.getId());
                        rescattable.createRestaurantsCategories(restcat);
                    }
                    categorytable.close();
                    rescattable.close();

                     // also update the opening hours to opening hours table
                    resturanttimetable.open();
                    for( String e: opening){
                        String[] hours = e.split(" ");
                        String start = hours[1].substring(0, hours[1].indexOf(":"))+ hours[1].substring(hours[1].indexOf(":")+1, hours[1].length());
                        String end = hours[2].substring(0, hours[2].indexOf(":"))+ hours[2].substring(hours[2].indexOf(":")+1, hours[2].length());
//
                        resturanttimetable.createRestaurantTime(new RestaurantTime(add.getId(),hours[0], Integer.parseInt(start), Integer.parseInt(end)));
                    }
                    resturanttimetable.close();



                }

            }
            resturanttable.open();
            Restaurant temp = new Restaurant(street, city, rank, cost, name);
            ////System.out.println("street: "+street + "  city:  " + city +  "  rank:   " + rank + "   cost:   " + cost + " name:   " + name);

            //add restuarant to resturantable
            Restaurant add  = resturanttable.createRestaurant(temp);
            resturanttable.close();
            // add all category to category table and also add to resturant-category table
            categorytable.open();
            rescattable.open();
            for(String s: category){
                Category temp2 = new Category(s);
                Category temp1 = categorytable.createCategory(temp2);
                RestaurantsCategories restcat = new RestaurantsCategories(add.getId(), temp1.getId());
                rescattable.createRestaurantsCategories(restcat);
            }
            categorytable.close();
            rescattable.close();

            // also update the opening hours to opening hours table
            resturanttimetable.open();
            for( String e: opening){
                String[] hours = e.split(" ");
                String start = hours[1].substring(0, hours[1].indexOf(":"))+ hours[1].substring(hours[1].indexOf(":")+1, hours[1].length());
                String end = hours[2].substring(0, hours[2].indexOf(":"))+ hours[2].substring(hours[2].indexOf(":")+1, hours[2].length());
//
                resturanttimetable.createRestaurantTime(new RestaurantTime(add.getId(),hours[0], Integer.parseInt(start), Integer.parseInt(end)));
            }
            resturanttimetable.close();

        }



        catch(IOException e){
            System.out.println("NoFileFound");
        }

    }

    // convert arrary of string into arraylist of string
    public static ArrayList<String> convert(String[] str){
        ArrayList<String> list = new ArrayList<String>();
        for( int i =0; i < str.length; i++){
            list.add(str[i]);
        }
        return list;
    }

    // count the number of dollar sign in the string
    public static int count(String st){
        int count =0;
        for(int i=0; i< st.length() ;i++){
            if(st.charAt(i) == '$') count++;
        }
        return count;
    }


}