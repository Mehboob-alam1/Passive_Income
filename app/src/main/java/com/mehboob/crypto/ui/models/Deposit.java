package com.mehboob.crypto.ui.models;

public class Deposit {

    private String screenShot;
    private String userId;
    private String timeStamp;
    private String depositBalance;
    private boolean isApproved;
    private String pushId;
    private String totalBalance;
    private String depositAccount;


    public Deposit() {
    }

    public Deposit(String screenShot, String userId, String timeStamp, String depositBalance, boolean isApproved, String pushId, String totalBalance, String depositAccount) {
        this.screenShot = screenShot;
        this.userId = userId;
        this.timeStamp = timeStamp;
        this.depositBalance = depositBalance;
        this.isApproved = isApproved;
        this.pushId = pushId;
        this.totalBalance = totalBalance;
        this.depositAccount = depositAccount;
    }

    public String getDepositAccount() {
        return depositAccount;
    }

    public void setDepositAccount(String depositAccount) {
        this.depositAccount = depositAccount;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getDepositBalance() {
        return depositBalance;
    }

    public void setDepositBalance(String depositBalance) {
        this.depositBalance = depositBalance;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getScreenShot() {
        return screenShot;
    }

    public void setScreenShot(String screenShot) {
        this.screenShot = screenShot;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
