package com.mehboob.passiveincome.ui.models;

public class Accounts {
    private String bankAccountNumber;
    private String bankAccountName;
    private String jazzAccountNumber;
    private String jazzAccountName;
    private String easyAccountNumber;
    private String easyAccountName;


    public Accounts() {
    }

    public Accounts(String bankAccountNumber, String bankAccountName, String jazzAccountNumber, String jazzAccountName, String easyAccountNumber, String easyAccountName) {
        this.bankAccountNumber = bankAccountNumber;
        this.bankAccountName = bankAccountName;
        this.jazzAccountNumber = jazzAccountNumber;
        this.jazzAccountName = jazzAccountName;
        this.easyAccountNumber = easyAccountNumber;
        this.easyAccountName = easyAccountName;
    }


    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getJazzAccountNumber() {
        return jazzAccountNumber;
    }

    public void setJazzAccountNumber(String jazzAccountNumber) {
        this.jazzAccountNumber = jazzAccountNumber;
    }

    public String getJazzAccountName() {
        return jazzAccountName;
    }

    public void setJazzAccountName(String jazzAccountName) {
        this.jazzAccountName = jazzAccountName;
    }

    public String getEasyAccountNumber() {
        return easyAccountNumber;
    }

    public void setEasyAccountNumber(String easyAccountNumber) {
        this.easyAccountNumber = easyAccountNumber;
    }

    public String getEasyAccountName() {
        return easyAccountName;
    }

    public void setEasyAccountName(String easyAccountName) {
        this.easyAccountName = easyAccountName;
    }
}
