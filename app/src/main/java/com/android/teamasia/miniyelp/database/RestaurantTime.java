package com.android.teamasia.miniyelp.database;

/**
 * Created by rameel on 9/20/14.
 */
public class RestaurantTime {
    private long restaurantId;
    private String day;
    private int startTime;
    private int endTime;

    /**
     * Constructor
     * @param restaurantId Restaurant ID
     * @param day Day
     * @param startTime Opening time
     * @param endTime Closing time
     */
    public RestaurantTime(long restaurantId, String day, int startTime, int endTime) {
        this.restaurantId = restaurantId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Gets restaurant ID
     * @return Restaurant ID
     */
    public long getRestaurantId() {
        return restaurantId;
    }

    /**
     * Sets restaurant ID
     * @param restaurantId Restaurant ID to set
     */
    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    /**
     * Gets day of week
     * @return Day of week
     */
    public String getDay() {
        return day;
    }

    /**
     * Sets day of week
     * @param day Day of week to set
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Gets opening time
     * @return Opening time
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Sets opening time
     * @param startTime Opening time to set
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns closing time
     * @return Closing time
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Sets closing time
     * @param endTime Closing time to set
     */
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
