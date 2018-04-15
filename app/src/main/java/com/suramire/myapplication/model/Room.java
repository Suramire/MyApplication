package com.suramire.myapplication.model;

import java.io.Serializable;


public class Room implements Serializable {
    int id;
    int houseid;
    String name;

    public int getIsLended() {
        return isLended;
    }

    public void setIsLended(int isLended) {
        this.isLended = isLended;
    }

    int isLended;

    public Room(int houseid, String name, float price) {
        this.houseid = houseid;
        this.name = name;
        this.price = price;
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
