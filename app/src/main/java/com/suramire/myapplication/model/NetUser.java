package com.suramire.myapplication.model;

import java.util.List;


public class NetUser {

    /**
     * id : 109270
     * bankCards : []
     * certification : null
     * idCard : null
     * phone : 18850508860
     * email : null
     * name : 张三
     * adminType : 6
     * gender : 1
     */

    private int id;
    private Object certification;
    private Object idCard;
    private String phone;
    private Object email;
    private String name;
    private int adminType;
    private int gender;
    private List<?> bankCards;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getCertification() {
        return certification;
    }

    public void setCertification(Object certification) {
        this.certification = certification;
    }

    public Object getIdCard() {
        return idCard;
    }

    public void setIdCard(Object idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdminType() {
        return adminType;
    }

    public void setAdminType(int adminType) {
        this.adminType = adminType;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public List<?> getBankCards() {
        return bankCards;
    }

    public void setBankCards(List<?> bankCards) {
        this.bankCards = bankCards;
    }
}
