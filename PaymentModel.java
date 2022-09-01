package com.example.sizzlingbites.ui.admin.model;

public class PaymentModel {
    String userAmount;
    String userUPI;
    String userName;
    String userEmail;
    String userNumber;
    String userNote;

    public PaymentModel(String userAmount, String userUPI, String userName, String userEmail, String userNumber, String userNote) {
        this.userAmount = userAmount;
        this.userUPI = userUPI;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userNumber = userNumber;
        this.userNote = userNote;
    }

    public PaymentModel() {

    }

    public String getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(String userAmount) {
        this.userAmount = userAmount;
    }

    public String getUserUPI() {
        return userUPI;
    }

    public void setUserUPI(String userUPI) {
        this.userUPI = userUPI;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }
}
