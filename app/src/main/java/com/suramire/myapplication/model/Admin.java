package com.suramire.myapplication.model;


//实体类-房东


public class Admin {
    int id;//编号
    String name;//用户名(手机号)
    String password;//密码
    String nickname;//昵称

    public Admin(String name, String password,String nickname) {
        this.name = name;
        this.password = password;
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
