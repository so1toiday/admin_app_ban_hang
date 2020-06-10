package com.quyet.banhang.admin_app_ban_hang.model;

public class ThongKe implements Comparable<ThongKe> {
    String key;
    int count;

    public ThongKe() {
    }

    public ThongKe(String key, int count) {
        this.key = key;
        this.count = count;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo(ThongKe o) {
        if (this.count > o.count) {
            return 1;
        } else if (this.count < o.count) {
            return -1;
        } else {
            return 0;
        }
    }
}
