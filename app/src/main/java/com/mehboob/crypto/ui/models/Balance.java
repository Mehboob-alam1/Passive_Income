package com.mehboob.crypto.ui.models;

public class Balance {

    private String totalBalance;
    private String userId;

    public Balance(String totalBalance, String userId) {
        this.totalBalance = totalBalance;
        this.userId = userId;
    }

    public Balance() {
    }


    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
