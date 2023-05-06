package com.mehboob.passiveincome.ui.models;

public class Withdraw {
    private String userId;
    private String pushId;
    private boolean isVerified;
    private String withDrawAmount;
    private String withDrawAccountNumber;
    private String WithDrawAccountName;
    private String timeStamp;

    public Withdraw() {
    }

    public Withdraw(String userId, String pushId, boolean isVerified, String withDrawAmount, String withDrawAccountNumber, String withDrawAccountName, String timeStamp) {
        this.userId = userId;
        this.pushId = pushId;
        this.isVerified = isVerified;
        this.withDrawAmount = withDrawAmount;
        this.withDrawAccountNumber = withDrawAccountNumber;
        WithDrawAccountName = withDrawAccountName;
        this.timeStamp = timeStamp;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getWithDrawAmount() {
        return withDrawAmount;
    }

    public void setWithDrawAmount(String withDrawAmount) {
        this.withDrawAmount = withDrawAmount;
    }

    public String getWithDrawAccountNumber() {
        return withDrawAccountNumber;
    }

    public void setWithDrawAccountNumber(String withDrawAccountNumber) {
        this.withDrawAccountNumber = withDrawAccountNumber;
    }

    public String getWithDrawAccountName() {
        return WithDrawAccountName;
    }

    public void setWithDrawAccountName(String withDrawAccountName) {
        WithDrawAccountName = withDrawAccountName;
    }
}
