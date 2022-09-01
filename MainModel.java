package com.example.sizzlingbites.ui.user.home.model;

import java.io.Serializable;

public class MainModel implements Serializable {
    String name;
    String description;
    int discountPrice;
    int price;
    String rating;
    String image_url;

    public MainModel(String name, String description, int discountPrice, int price, String rating, String image_url) {
        this.name = name;
        this.description = description;
        this.discountPrice = discountPrice;
        this.price = price;
        this.rating = rating;
        this.image_url = image_url;
    }

    public MainModel() {

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

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
