package com.android.teamasia.miniyelp;

/**
 * Created by kienhoang on 9/20/14.
 */
public class Review {
    private long id;
    private String content;
    private long restaurantId;
    private long reviewerId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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
