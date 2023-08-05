package com.mehboob.crypto.ui.models;

public class Slider {

    private String imageLink;
    private String imageUrl;
    private String pushId;

    public Slider() {
    }

    public Slider(String imageLink, String imageUrl, String pushId) {
        this.imageLink = imageLink;
        this.imageUrl = imageUrl;
        this.pushId = pushId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
