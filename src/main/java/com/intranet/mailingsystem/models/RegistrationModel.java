package com.intranet.mailingsystem.models;


public class RegistrationModel {
    private int userId;
    private String userEmail;
    private String userAltrEmail;
    private String userFirstName;
    private String userLastName;
    private String userPassword;
    private int userPhone;
    private String userBirthdate;
    private String corporationName;


    public String getUserAltrEmail() {
        return userAltrEmail;
    }

    public void setUserAltrEmail(String userAltrEmail) {
        this.userAltrEmail = userAltrEmail;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(int userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserBirthdate() {
        return userBirthdate;
    }

    public void setUserBirthdate(String userBirthString) {
        this.userBirthdate = userBirthString;
    }

    public String getCorporationName() {
        return corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }
}