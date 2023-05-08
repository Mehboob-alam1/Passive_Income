package com.mehboob.passiveincome.ui.models;

import android.widget.TextView;

public class Invited {

    String txtUserNameAccount,txtUserAmountAccount,txtUserJoinTimeAccount;

    public Invited(String txtUserNameAccount, String txtUserAmountAccount, String txtUserJoinTimeAccount) {
        this.txtUserNameAccount = txtUserNameAccount;
        this.txtUserAmountAccount = txtUserAmountAccount;
        this.txtUserJoinTimeAccount = txtUserJoinTimeAccount;
    }

    public Invited() {
    }

    public String getTxtUserNameAccount() {
        return txtUserNameAccount;
    }

    public void setTxtUserNameAccount(String txtUserNameAccount) {
        this.txtUserNameAccount = txtUserNameAccount;
    }

    public String getTxtUserAmountAccount() {
        return txtUserAmountAccount;
    }

    public void setTxtUserAmountAccount(String txtUserAmountAccount) {
        this.txtUserAmountAccount = txtUserAmountAccount;
    }

    public String getTxtUserJoinTimeAccount() {
        return txtUserJoinTimeAccount;
    }

    public void setTxtUserJoinTimeAccount(String txtUserJoinTimeAccount) {
        this.txtUserJoinTimeAccount = txtUserJoinTimeAccount;
    }
}
