package com.mehboob.crypto.ui.models;

public class Packages {

    private String profit;
    private String packageType;
    private String startRange;
    private String lastRange;


    public Packages() {
    }

    public Packages(String profit, String packageType, String startRange, String lastRange) {
        this.profit = profit;
        this.packageType = packageType;
        this.startRange = startRange;
        this.lastRange = lastRange;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getStartRange() {
        return startRange;
    }

    public void setStartRange(String startRange) {
        this.startRange = startRange;
    }

    public String getLastRange() {
        return lastRange;
    }

    public void setLastRange(String lastRange) {
        this.lastRange = lastRange;
    }
}
