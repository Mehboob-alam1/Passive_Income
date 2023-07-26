package com.mehboob.passiveincome.ui.models;

public class User {

    private String email,password,first_name,sur_name,phone_number,referral_id,address,user_id,user_image,cnic_front,cnic_back,dateAuth;
    private boolean isBlock,isComplete;



    public User() {
    }

    public User(String email, String password, String first_name, String sur_name, String phone_number, String referral_id, String address, String user_id, String user_image, String cnic_front, String cnic_back, boolean isBlock, boolean isComplete, String dateAuth) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.sur_name = sur_name;
        this.phone_number = phone_number;
        this.referral_id = referral_id;
        this.address = address;
        this.user_id = user_id;
        this.user_image = user_image;
        this.cnic_front = cnic_front;
        this.cnic_back = cnic_back;
        this.isBlock = isBlock;
        this.isComplete = isComplete;
        this.dateAuth=dateAuth;
    }


    public String getDateAuth() {
        return dateAuth;
    }

    public void setDateAuth(String dateAuth) {
        this.dateAuth = dateAuth;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public String getCnic_front() {
        return cnic_front;
    }

    public void setCnic_front(String cnic_front) {
        this.cnic_front = cnic_front;
    }

    public String getCnic_back() {
        return cnic_back;
    }

    public void setCnic_back(String cnic_back) {
        this.cnic_back = cnic_back;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSur_name() {
        return sur_name;
    }

    public void setSur_name(String sur_name) {
        this.sur_name = sur_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getReferral_id() {
        return referral_id;
    }

    public void setReferral_id(String referral_id) {
        this.referral_id = referral_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
