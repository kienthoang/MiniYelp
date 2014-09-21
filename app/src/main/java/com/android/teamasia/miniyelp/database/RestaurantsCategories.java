package com.android.teamasia.miniyelp.database;

/**
 * Created by rameel on 9/20/14.
 */
public class RestaurantsCategories {
    private long restaurantId;
    private long categoryId;

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long category_id) {
        this.categoryId = category_id;
    }
}
