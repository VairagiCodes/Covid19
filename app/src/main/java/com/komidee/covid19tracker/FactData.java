package com.komidee.covid19tracker;

import androidx.annotation.Nullable;

public class FactData {
    private String title;
    private int imgURL;
    private String id;
    private boolean state = false;

    public FactData(String title , @Nullable int imgURL) {

        this.title = title;
        this.imgURL = imgURL;
    }

    public int getImgURL() { return imgURL;
    }

    public void setImgURL(int imgURL) {
        this.imgURL = imgURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return  id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setState(boolean state) {
        this.state = state;
    }
    public  boolean getState() {
        return state;
    }

}
