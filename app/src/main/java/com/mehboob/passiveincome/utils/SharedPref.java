package com.mehboob.passiveincome.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private SharedPreferences sharedPreferences;
    private static final String ACCOUNT_SELECTED="account";
    private static final String TOTAL_BALANCE="balance";
    private static final String IS_PROFILE_COMPLETE="profile";
    private static final String INFO_USER="user";

    public SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(ACCOUNT_SELECTED, MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(TOTAL_BALANCE, MODE_PRIVATE);
        sharedPreferences=context.getSharedPreferences(IS_PROFILE_COMPLETE,MODE_PRIVATE);
        sharedPreferences=context.getSharedPreferences(INFO_USER, MODE_PRIVATE);
    }

    public void saveIsComplete(boolean isComplete){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_PROFILE_COMPLETE,isComplete);
        editor.apply();
    }
public boolean fetchIsComplete(){
    return sharedPreferences.getBoolean(IS_PROFILE_COMPLETE,false);
}
    public void saveIsUser(boolean isUser){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(INFO_USER,isUser);
        editor.apply();
    }
    public boolean fetchIsUser(){
        return sharedPreferences.getBoolean(INFO_USER,false);
    }
    public void saveAccount(String account){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCOUNT_SELECTED, account);
        editor.apply();
    }
    public void saveBalance(String balance){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOTAL_BALANCE, balance);
        editor.apply();
    }

    public String fetchAccount(){

        return sharedPreferences.getString(ACCOUNT_SELECTED,"Easypaisa");
    }
    public String fetchBalance(){

        return sharedPreferences.getString(TOTAL_BALANCE,"0");
    }

}
