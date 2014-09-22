package com.android.teamasia.miniyelp.database;

/**
 * Created by rameel on 9/20/14.
 */
public class Restaurant {
    private long id;
    private String street;
    private String city;
    private double rank;
    private int cost;
    private String name;
    private int reviewers;

    // !!!empty constructor still needed
    public Restaurant() {
        street = "";
        city = "";
        rank = 0;
        name = "";
        reviewers = 0;
    }

    public Restaurant(String street, String city, double rank, int cost, String name,
                      int reviewers) {
        this.street = street;
        this.city = city;
        this.rank = rank;
        this.cost = cost;
        this.name = name;
        this.reviewers = reviewers;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getReviewers() {
        return reviewers;
    }

    public void setReviewers(int reviewers) {
        this.reviewers = reviewers;
    }
}
