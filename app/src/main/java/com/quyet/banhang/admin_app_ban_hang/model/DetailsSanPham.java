package com.quyet.banhang.admin_app_ban_hang.model;

import java.io.Serializable;

public class DetailsSanPham implements Serializable {
    String color;
    String Size;
    int price;
    int count;

    public DetailsSanPham(String color, String size, int price, int count) {
        this.color = color;
        Size = size;
        this.price = price;
        this.count = count;
    }



    public DetailsSanPham() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
