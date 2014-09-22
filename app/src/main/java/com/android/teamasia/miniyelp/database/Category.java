package com.android.teamasia.miniyelp.database;

/**
 * Created by rameel on 9/20/14.
 */
public class Category {
    private long id;
    private String title;

    public Category() {
    }

    public Category(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
