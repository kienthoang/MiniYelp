package com.android.teamasia.miniyelp;
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
            // variables
            int rank;
            String cost ;
            ArrayList<String> opening;
            String name;
            String street;
            ArrayList<String> cateogory;
            String reviewers;
            String city;

            //database table
            CategoryTable categorytable = new CategoryTable();
            RestaurantTable resturanttable = new RestaurantTable();
            RestaurantTimesTable resturanttimetable = new RestaurantTimesTable();
            RestaurantsCategoriesTable rescattable = new RestaurantsCategoriesTable();


            // check for the end of the text file
            while(sc.hasNext()){

                String currentline = sc.nextLine();
//   System.out.println(currentline);

                if(!currentline.isEmpty()){
                    String variable = currentline.substring(0,2);

                    //rank
//   System.out.print(variable.equals("ra"));
                    if(variable.equals("ra")){
                        rank =  Integer.parseInt(currentline.substring(currentline.indexOf(" ")+1, currentline.length()));
                        System.out.println(rank);
                    }
                    //cost
                    else if(variable.equals("co")){
                        cost =  currentline.substring(currentline.indexOf(" ")+1, currentline.length());
                        System.out.println(cost);
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
                        System.out.println(address);
                    }
                    //name
                    else if(variable.equals("na")){
                        name =  currentline.substring(currentline.indexOf(" ")+1, currentline.length());
                        System.out.println(name);
                    }
                    // cateogory
                    else if(variable.equals("ca")){
                        String[] first = currentline.split(" ");
                        String cateogorylist =  first[1];
                        String[] cateogory1 = cateogorylist.split(",");
                        cateogory = convert(cateogory1);
                        for(String s: cateogory){
                            System.out.println(s);
                        }
                    }

                    // reviewers
                    else if(variable.equals("re")){
                        reviewers =  currentline.substring(currentline.indexOf(" ")+1, currentline.length());
                        System.out.println(reviewers);
                    }
                    //city
                    else if(variable.equals("ci")){
                        city =  currentline.substring(currentline.indexOf(" ")+1, currentline.length());
                        System.out.println(city);
                    }
                }
                else{

                    System.out.println("End of the block");

                    // create a new resturant
                    Resturant temp = new Restaurant(street, city, rank, cost, name));

                    // add resutrant to resturantable
                    Resturant add  = resturanttable.createResturant(temp);

                    // add all category to category table and also add to resturant-cateogory table
                    for(int i =0; i < cateogory.length < i++){
                        Category temp = new Cateogory(cateogory[i]);
                        Category temp1 = categorytable.createCategory(temp);
                        RestaurantsCategories restcat = new RestaurantsCategories(add.getId(), temp1.getId());
                        rescattable.addcreateRestaurantsCategories(restcat);
                    }

                    // also update the opening hours to opening hours table
                    for( String e: opening){
                        String[] hours = e.split(" ");
                        resturanttimetable.createRestaurantTime(new ResturantTime(add.getId(), hours[0], hours[1], hours[2]));
                    }



                }

            }
        }



        catch(IOException e){
            System.out.println("Exception Found");
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