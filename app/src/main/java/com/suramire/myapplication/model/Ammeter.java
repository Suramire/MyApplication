package com.suramire.myapplication.model;

/**
 * Created by Suramire on 2017/10/12.
 */

public class Ammeter {
    int id;
    int mRoomid;


    String mRoomName;//房间名
    int mLastcount;//上次读数
    int mCount;//本次读数
    String mLastTime;//上次读取时间
//    String mTime;//本次读取时间

    public Ammeter(int id, int roomid, int count) {
        this.id = id;
        this.mRoomid = roomid;
        this.mCount = count;
//        mTime = time;
    }

    public Ammeter(int id, int roomid, int count,int lastcount) {
        this.id = id;
        this.mRoomid = roomid;
        this.mCount = count;
        this.mLastcount = lastcount;
//        mTime = time;
    }

    public Ammeter(int roomid, int count) {
        this.mRoomid = roomid;
        this.mCount = count;
//        this.mTime = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomid() {
        return mRoomid;
    }

    public void setRoomid(int roomid) {
        this.mRoomid = roomid;
    }

    public int getLastcount() {
        return mLastcount;
    }

    public void setLastcount(int lastcount) {
        this.mLastcount = lastcount;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    public String getLastTime() {
        return mLastTime;
    }

    public void setLastTime(String lastTime) {
        this.mLastTime = lastTime;
    }

    public String getRoomName() {
        return mRoomName;
    }

    public void setRoomName(String roomName) {
        mRoomName = roomName;
    }


}
