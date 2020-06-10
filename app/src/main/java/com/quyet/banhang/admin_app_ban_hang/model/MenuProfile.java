package com.quyet.banhang.admin_app_ban_hang.model;

public class MenuProfile {
    String title;

    int image;

    public MenuProfile(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public MenuProfile() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }
}
