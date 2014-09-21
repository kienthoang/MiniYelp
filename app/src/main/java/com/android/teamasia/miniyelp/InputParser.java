package com.android.teamasia.miniyelp;

import com.android.teamasia.miniyelp.database;

import  java.util.*;
import java.io.*;
/**
 * Created by kienhoang on 9/20/14.
 * Modified by bikram on 9/20/14.
 */
public class InputParser {

    public static void main(String args[]){
        parseInputBlock("sample.txt");
    }

    public static void parseInputBlock(String block)  {

        try{

            File inputFile = new File(block);
            Scanner sc = new Scanner(inputFile);
            // variables
            int rank =0;
            String cost = "" ;
            ArrayList<String> opening = new ArrayList<String>();
            String name= "";
            String street=  "";
            ArrayList<String> cateogory = new ArrayList<String>();
            String reviewers;
            String city= "";

            //database table
            CategoryTable categorytable = new CategoryTable();
            RestaurantTable resturanttable = new RestaurantTable();
            RestaurantTimesTable resturanttimetable = new RestaurantTimesTable();
            RestaurantsCategoriesTable rescattable = new RestaurantsCategoriesTable();


            // check for the end of the text file
            while(sc.hasNext()){

                String currentline = sc.nextLine();


                if(!currentline.isEmpty()){
                    String variable = currentline.substring(0,2);

                    //rank

                    if(variable.equals("ra")){
                        rank =  Integer.parseInt(currentline.substring(currentline.indexOf(" ")+1, currentline.length()));

                    }
                    //cost
                    else if(variable.equals("co")){
                        cost =  currentline.substring(currentline.indexOf(" ")+1, currentline.length());

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

                    // cateogory
                    else if(variable.equals("ca")){
                        String first = currentline.substring(currentline.indexOf(" ")+1, currentline.length());
                        String[] cateogory1 = first.split(",");
                        cateogory = convert(cateogory1);
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

                    System.out.println("End of the block");

                    //create a new resturant
                    Resturant temp = new Restaurant(street, city, rank, cost, name));
                    //System.out.println(street +"  " + city +  "   " + rank + " " + cost + "  " + name);

                    //add resutrant to resturantable
                    Resturant add  = resturanttable.createResturant(temp);

                    // add all category to category table and also add to resturant-cateogory table
                    for(String s: cateogory){
                        System.out.println(s);
                        Category temp = new Cateogory(cateogory[i]);
                        Category temp1 = categorytable.createCategory(temp);
                        RestaurantsCategories restcat = new RestaurantsCategories(add.getId(), temp1.getId());
                        rescattable.addcreateRestaurantsCategories(restcat);
                    }
//    
//    // also update the opening hours to opening hours table
                    for( String e: opening){
                        String[] hours = e.split(" ");
                        String start = hours[1].substring(0, hours[1].indexOf(":"))+ hours[1].substring(hours[1].indexOf(":")+1, hours[1].length());
                        String end = hours[2].substring(0, hours[2].indexOf(":"))+ hours[2].substring(hours[2].indexOf(":")+1, hours[2].length());
//      
                        //System.out.println( hours[0] + "  " + start + "  " + end);
                        resturanttimetable.createRestaurantTime(new ResturantTime(add.getId(),hours[0], Integer.parseInt(start), Integer.parseInt(end));
                    }



                }

            }
        }



        catch(IOException e){
            System.out.println("fuck yourself");
        }

    }

    // seperate string by delimeter
    public static ArrayList<String> convert(String[] str){
        ArrayList<String> list = new ArrayList<String>();
        for( int i =0; i < str.length; i++){
            list.add(str[i]);
        }
        return list;
    }



}