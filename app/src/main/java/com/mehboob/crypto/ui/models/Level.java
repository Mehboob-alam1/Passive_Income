package com.mehboob.crypto.ui.models;

public class Level {


    private String userName,amount,joinTime;

    public Level(String userName, String amount, String joinTime) {
        this.userName = userName;
        this.amount = amount;
        this.joinTime = joinTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }
}
