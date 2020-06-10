package com.quyet.banhang.admin_app_ban_hang.model;

import java.io.Serializable;

public class ThongBao implements Serializable {
    String id;
    String Title;
    String Content;
    String Image;
    String Date;

    public ThongBao() {
    }

    public ThongBao(String id, String title, String content, String image, String date) {
        this.id = id;
        Title = title;
        Content = content;
        Image = image;
        Date = date;
    }

    public ThongBao(String title, String content, String image, String date) {
        Title = title;
        Content = content;
        Image = image;
        Date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
