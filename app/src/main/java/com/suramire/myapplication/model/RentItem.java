package com.suramire.myapplication.model;

import java.io.Serializable;

/**
 * Created by Suramire on 2017/9/21.
 */

public class RentItem  implements Serializable{
    int rentInfoId;

    public int getRentInfoId() {
        return rentInfoId;
    }

    public void setRentInfoId(int rentInfoId) {
        this.rentInfoId = rentInfoId;
    }

    String renterName;
    String rentRoomName;
    String money;
    String margin;
    String date_begin;
    String date_end;

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
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

    public RentItem(int rentInfoId, String renterName, String rentRoomName, String money, String margin, String date_begin, String date_end) {
        this.rentInfoId = rentInfoId;
        this.renterName = renterName;
        this.rentRoomName = rentRoomName;
        this.money = money;
        this.margin = margin;
        this.date_begin = date_begin;
        this.date_end = date_end;
    }

    public String getRenterName() {

        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    public String getRentRoomName() {
        return rentRoomName;
    }

    public void setRentRoomName(String rentRoomName) {
        this.rentRoomName = rentRoomName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
