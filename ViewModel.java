package com.example.sizzlingbites.ui.user.home.model;

import java.io.Serializable;

public class ViewModel implements Serializable {
    String name;
    String description;
    int discountPrice;
    int price;
    String image_url;
    String rating;
    String type;

    public ViewModel(String name, String description, int discountPrice, int price, String image_url, String rating, String type) {
        this.name = name;
        this.description = description;
        this.discountPrice = discountPrice;
        this.price = price;
        this.image_url = image_url;
        this.rating = rating;
        this.type = type;
    }

    public ViewModel() {

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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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
}
