package com.suramire.myapplication.model;

/**
 * Created by Suramire on 2017/9/19.
 */

public class Admin {
    int id;
    String name;
    String password;

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
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


}
