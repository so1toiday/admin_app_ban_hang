package com.quyet.banhang.admin_app_ban_hang.model;


public class User
{
    private String UID;
    private  String Email,Name,Phone,Birthday,Image,Address;
    private  boolean Sex;

    public String getAddress() {
        return Address;
    }

    public User(String UID, String email, String name, String phone, String birthday, String image, String address, boolean sex) {
        this.UID = UID;
        Email = email;
        Name = name;
        Phone = phone;
        Birthday = birthday;
        Image = image;
        Address = address;
        Sex = sex;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getUID() {
        return UID;
    }

    public User(String UID, String email, String name, String phone, String birthday, boolean sex) {
        this.UID = UID;
        Email = email;
        Name = name;
        Phone = phone;
        Birthday = birthday;
        Sex = sex;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public User(String UID, String email, String name, String phone, String birthday, String image, boolean sex) {
        this.UID = UID;
        Email = email;
        Name = name;
        Phone = phone;
        Birthday = birthday;
        Image = image;
        Sex = sex;
    }

    public User() {
    }

    public User(String email, String name, String phone, String birthday, boolean sex) {
        Email = email;
        Name = name;
        Phone = phone;
        Birthday = birthday;
        Sex = sex;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }


    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public boolean isSex() {
        return Sex;
    }

    public void setSex(boolean sex) {
        Sex = sex;
    }
}
