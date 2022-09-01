package com.example.sizzlingbites.ui.user.home.model;

import java.io.Serializable;

public class MainCourseModel implements Serializable {
    String image_url;
    String name;
    String desc;
    int discountPrice;
    int price;
    String rating;
    String type;

    public MainCourseModel(String image_url, String name, String desc, int discountPrice, int price, String rating, String type) {
        this.image_url = image_url;
        this.name = name;
        this.desc = desc;
        this.discountPrice = discountPrice;
        this.price = price;
        this.rating = rating;
        this.type = type;
    }

    public MainCourseModel() {

    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
