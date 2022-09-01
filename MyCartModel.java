package com.example.sizzlingbites.ui.user.home.model;

import java.io.Serializable;

public class MyCartModel implements Serializable {
    String saveCurrentDate;
    String saveCurrentTime;
    String itemName;
    String itemPrice;
    String itemQuantity;
    String documentId;
    int discount;
    int totalPrice;

    public MyCartModel(String saveCurrentDate, String saveCurrentTime, String itemName, String itemPrice, String itemQuantity, String documentId, int discount, int totalPrice) {
        this.saveCurrentDate = saveCurrentDate;
        this.saveCurrentTime = saveCurrentTime;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.documentId = documentId;
        this.discount = discount;
        this.totalPrice = totalPrice;
    }

    public MyCartModel() {

    }

    public String getSaveCurrentDate() {
        return saveCurrentDate;
    }

    public void setSaveCurrentDate(String saveCurrentDate) {
        this.saveCurrentDate = saveCurrentDate;
    }

    public String getSaveCurrentTime() {
        return saveCurrentTime;
    }

    public void setSaveCurrentTime(String saveCurrentTime) {
        this.saveCurrentTime = saveCurrentTime;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
