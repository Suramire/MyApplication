package com.suramire.myapplication.model;


//实体类-房源

public class House {
    int id;
    String address;//详细地址
    String area;//区域
    String name;//楼名
    int adminid;//房东id

    public int getAdminid() {
        return adminid;
    }

    public void setAdminid(int adminid) {
        this.adminid = adminid;
    }

    public House() {

    }

    public House(String address, String area, String name, int adminid, int floor) {
        this.address = address;
        this.area = area;
        this.name = name;
        this.adminid = adminid;
        this.floor = floor;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    int floor;

}
