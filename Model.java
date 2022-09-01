package com.example.sizzlingbites.ui.user.home.model;

public class Model {
    String name;
    String description;
    String rating;
    String type;
    String image_url;

    public Model() {

    }

    public Model(String name, String description, String rating, String type, String image_url) {

        this.name = name;
        this.description = description;
        this.rating = rating;
        this.type = type;
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
