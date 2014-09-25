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

    /**
     * Constructor
     * @param street Street
     * @param city City
     * @param rank Rank
     * @param cost Cost
     * @param name Name
     * @param reviewers Reviewers
     */
    public Restaurant(String street, String city, double rank, int cost, String name,
                      int reviewers) {
        this.street = street;
        this.city = city;
        this.rank = rank;
        this.cost = cost;
        this.name = name;
        this.reviewers = reviewers;
    }

    /**
     * Returns street
     * @return Street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets street
     * @param street Street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Returns cost
     * @return Cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets cost
     * @param cost Cost to set
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Returns rank
     * @return Rank
     */
    public double getRank() {
        return rank;
    }

    /**
     * Sets rank
     * @param rank Rank to set
     */
    public void setRank(double rank) {
        this.rank = rank;
    }

    /**
     * Returns name
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     * @param name Name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns ID
     * @return ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets ID
     * @param id ID to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns city
     * @return City
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city
     * @param city City to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns reviewers
     * @return Reviewers
     */
    public int getReviewers() {
        return reviewers;
    }

    /**
     * Sets reviewers
     * @param reviewers Reviewers to set
     */
    public void setReviewers(int reviewers) {
        this.reviewers = reviewers;
    }
}
