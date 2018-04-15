package com.suramire.myapplication.model;


public class RentInfo {
    int id;

    String renterName;
    String renterPhone;
    int roomid;
    float money;
    float margin;
    String date_begin;
    String date_end;

    public RentInfo(String renterName, String renterPhone, int roomid, float money, float margin, String date_begin, String date_end) {
        this.renterName = renterName;
        this.renterPhone = renterPhone;
        this.roomid = roomid;
        this.money = money;
        this.margin = margin;
        this.date_begin = date_begin;
        this.date_end = date_end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    public String getRenterPhone() {
        return renterPhone;
    }

    public void setRenterPhone(String renterPhone) {
        this.renterPhone = renterPhone;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getMargin() {
        return margin;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }

    public String getDate_begin() {
        return date_begin;
    }

    public void setDate_begin(String date_begin) {
        this.date_begin = date_begin;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }
}
