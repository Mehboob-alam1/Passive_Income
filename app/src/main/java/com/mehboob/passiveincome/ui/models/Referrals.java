package com.mehboob.passiveincome.ui.models;

public class Referrals {
    private String userId;
    private String pushId;


    public Referrals() {
    }

    public Referrals(String userId, String pushId) {
        this.userId = userId;
        this.pushId = pushId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
