package com.suramire.myapplication.model;

/**
 * Created by Suramire on 2017/9/21.
 */

public class RentItem {
    int rentInfoId;

    public int getRentInfoId() {
        return rentInfoId;
    }

    public void setRentInfoId(int rentInfoId) {
        this.rentInfoId = rentInfoId;
    }

    String renterName;
    String rentRoomName;
    String Money;

    public RentItem(int rentInfoId, String renterName, String rentRoomName, String money) {
        this.rentInfoId = rentInfoId;
        this.renterName = renterName;
        this.rentRoomName = rentRoomName;
        Money = money;
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
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }
}
