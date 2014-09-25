package com.android.teamasia.miniyelp.database;

/**
 * Created by rameel on 9/20/14.
 */
public class Category {
    private long id;
    private String title;

    public Category() {
    }

    /**
     * Constructor
     * @param title Title
     */
    public Category(String title) {
        this.title = title;
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
     * Returns title
     * @return Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title
     * @param title Title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
