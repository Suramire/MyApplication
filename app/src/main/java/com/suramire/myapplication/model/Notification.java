package com.suramire.myapplication.model;

//实体类-工作提醒(备忘录)

public class Notification {
    int id;
    int adminid;//编写者id
    String content;//备忘内容
    String date;//备忘时间

    public Notification(String content, String date, int adminid) {
        this.content = content;
        this.date = date;
        this.adminid = adminid;
    }

    public int getAdminid() {
        return adminid;
    }

    public void setAdminid(int adminid) {
        this.adminid = adminid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
