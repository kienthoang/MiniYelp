package com.android.teamasia.miniyelp;

/**
 * Created by rameel on 9/20/14.
 */
public class RestaurantReviewers {
    private long restaurantId;
    private long reviewerId;

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(long reviewerId) {
        this.reviewerId = reviewerId;
    }
}
