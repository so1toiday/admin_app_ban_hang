package com.quyet.banhang.admin_app_ban_hang.model;

public class CommentModel {
    User user;
    String Comment;
    String image;
    String date;

    public CommentModel(User user, String comment, String image, String date) {
        this.user = user;
        Comment = comment;
        this.image = image;
        this.date = date;
    }

    public CommentModel(User user, String comment, String image) {
        this.user = user;
        Comment = comment;
        this.image = image;
    }

    public CommentModel() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
