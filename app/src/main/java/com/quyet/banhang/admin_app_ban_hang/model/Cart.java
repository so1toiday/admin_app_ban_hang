package com.quyet.banhang.admin_app_ban_hang.model;

import java.io.Serializable;

public class Cart implements Serializable {
    String idCart;
    String id;
    String name;
    String descreption;
    DetailsSanPham sanPham;
    String image;
    String category;
    int count;
    String date;
    String uid;
    String madonhang;

    String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(String madonhang) {
        this.madonhang = madonhang;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdCart() {
        return idCart;
    }

    public void setIdCart(String idCart) {
        this.idCart = idCart;
    }

    public Cart(String idCart, String id, String name, String descreption, DetailsSanPham sanPham, String image, String category, int count) {
        this.idCart = idCart;
        this.id = id;
        this.name = name;
        this.descreption = descreption;
        this.sanPham = sanPham;
        this.image = image;
        this.category = category;
        this.count = count;
    }

    public Cart(String id, String name, String descreption, DetailsSanPham sanPham, String image, String category, int count) {
        this.id = id;
        this.name = name;
        this.descreption = descreption;
        this.sanPham = sanPham;
        this.image = image;
        this.category = category;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescreption() {
        return descreption;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }

    public DetailsSanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(DetailsSanPham sanPham) {
        this.sanPham = sanPham;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Cart(String name, String descreption, DetailsSanPham sanPham, String image, String category, int count) {
        this.name = name;
        this.descreption = descreption;
        this.sanPham = sanPham;
        this.image = image;
        this.category = category;
        this.count = count;
    }

    public Cart() {
    }
}
