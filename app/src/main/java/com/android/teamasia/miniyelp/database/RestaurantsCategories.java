package com.android.teamasia.miniyelp.database;

/**
 * Created by rameel on 9/20/14.
 */

public class RestaurantsCategories {
    private long restaurantId;
    private long categoryId;

    /**
     * Constructor
     * @param restaurantId Restaurant ID
     * @param categoryId Category ID
     */
    public RestaurantsCategories(long restaurantId, long categoryId) {
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
    }

    /**
     * Returns restaurant ID
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
     * Returns category ID
     * @return Category ID
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * Sets category ID
     * @param category_id Category ID to set
     */
    public void setCategoryId(long category_id) {
        this.categoryId = category_id;
    }
}
