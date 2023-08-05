package com.mehboob.crypto.ui.models;

public class Accounts {
    private String usdtNumber;
    private String btcNumber;
    private String ethNumber;



    public Accounts() {
    }


    public Accounts(String usdtNumber, String btcNumber, String ethNumber) {
        this.usdtNumber = usdtNumber;
        this.btcNumber = btcNumber;
        this.ethNumber = ethNumber;
    }


    public String getUsdtNumber() {
        return usdtNumber;
    }

    public void setUsdtNumber(String usdtNumber) {
        this.usdtNumber = usdtNumber;
    }

    public String getBtcNumber() {
        return btcNumber;
    }

    public void setBtcNumber(String btcNumber) {
        this.btcNumber = btcNumber;
    }

    public String getEthNumber() {
        return ethNumber;
    }

    public void setEthNumber(String ethNumber) {
        this.ethNumber = ethNumber;
    }
}
