package com.mehboob.passiveincome.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private SharedPreferences sharedPreferences;
    private static final String ACCOUNT_SELECTED="account";

    public SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(ACCOUNT_SELECTED, MODE_PRIVATE);
    }

    public void saveAccount(String account){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCOUNT_SELECTED, account);
        editor.apply();
    }

    public String fetchAccount(){

        return sharedPreferences.getString(ACCOUNT_SELECTED,"Easypaisa");
    }


}
