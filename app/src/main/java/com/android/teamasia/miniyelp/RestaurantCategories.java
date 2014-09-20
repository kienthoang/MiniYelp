package com.android.teamasia.miniyelp;

/**
 * Created by rameel on 9/20/14.
 */
public class RestaurantCategories {
    private long restaurantId;
    private long categoryId;

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getCategory_id() {
        return categoryId;
    }

    public void setCategory_id(long category_id) {
        this.categoryId = category_id;
    }
}
