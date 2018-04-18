package com.suramire.myapplication.model;

import java.io.Serializable;


//实体类-房间

public class Room implements Serializable {
    int id;
    int houseid;//所属房源id
    String name;//房间名
    int isLended;//出租标志位

    public Room(int houseid, String name, float price) {
        this.houseid = houseid;
        this.name = name;
        this.price = price;
    }
    public int getIsLended() {
        return isLended;
    }

    public void setIsLended(int isLended) {
        this.isLended = isLended;
    }

    public Room() {

    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHouseid() {
        return houseid;
    }

    public void setHouseid(int houseid) {
        this.houseid = houseid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    float price;

}
