package com.idrisdemir.badapp.Entity;

public class New {

    private String uuid;

    private String title;

    private String description;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;


    public New(){

    }

    public New(String uuid, String title, String description, String imageUrl) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
