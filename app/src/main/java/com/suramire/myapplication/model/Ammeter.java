package com.suramire.myapplication.model;

import android.support.annotation.NonNull;

import java.io.Serializable;


public class Ammeter implements Serializable,Comparable<Ammeter> {
    int id;
    int mRoomid;

    String mRoomName;//房间名
    int mLastcount;//上次读数
    int mCount;//本次读数
    String mLastTime;//上次读取时间
    String mTime;

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        this.mTime = time;
    }

    int mSort;//排序序号

    public int getSort() {
        return mSort;
    }

    public void setSort(int sort) {
        mSort = sort;
    }
    //    String mTime;//本次读取时间

    public Ammeter(int id, int roomid, int count) {
        this.id = id;
        this.mRoomid = roomid;
        this.mCount = count;
//        mTime = mTime;
    }

    public Ammeter(int id, int roomid, int count,int lastcount,int sort) {
        this.id = id;
        this.mRoomid = roomid;
        this.mCount = count;
        this.mLastcount = lastcount;
        this.mSort = sort;
//        mTime = mTime;
    }

    public Ammeter(int roomid, int count) {
        this.mRoomid = roomid;
        this.mCount = count;
//        this.mTime = mTime;
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

    @Override
    public int compareTo(@NonNull Ammeter o) {
        return o.getSort() - getSort() ;
    }
}
