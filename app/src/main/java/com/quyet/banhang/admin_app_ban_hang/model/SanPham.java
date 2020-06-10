package com.quyet.banhang.admin_app_ban_hang.model;

import java.io.Serializable;
import java.util.List;

public class SanPham  implements Serializable {
    String id;
    String name;
    String descreption;
    List<DetailsSanPham> sanPhams;
    List<String> image;
    double star;
    String category;
    List<String> key;
    int daban;

    public int getDaban() {
        return daban;
    }

    public void setDaban(int daban) {
        this.daban = daban;
    }

    public List<String> getKey() {
        return key;
    }

    public void setKey(List<String> key) {
        this.key = key;
    }

    public SanPham() {
    }

    public SanPham(String id, String name, String descreption, List<DetailsSanPham> sanPhams, List<String> image, double star, String category) {
        this.id = id;
        this.name = name;
        this.descreption = descreption;
        this.sanPhams = sanPhams;
        this.image = image;
        this.star = star;
        this.category = category;
    }

    public SanPham(String name, String descreption, List<DetailsSanPham> sanPhams, List<String> image, double star, String category) {
        this.name = name;
        this.descreption = descreption;
        this.sanPhams = sanPhams;
        this.image = image;
        this.star = star;
        this.category = category;
    }

    public List<DetailsSanPham> getSanPhams() {
        return sanPhams;
    }

    public void setSanPhams(List<DetailsSanPham> sanPhams) {
        this.sanPhams = sanPhams;
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


    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
