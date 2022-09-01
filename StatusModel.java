package com.example.sizzlingbites.ui.admin.model;

public class StatusModel {
    String orderDate;
    String orderTime;
    String orderName;
    String orderQuantity;
    String orderStatus;

    public StatusModel(String orderDate, String orderTime, String orderName, String orderQuantity, String orderStatus) {
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderName = orderName;
        this.orderQuantity = orderQuantity;
        this.orderStatus = orderStatus;
    }

    public StatusModel() {

    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
