package com.example.sqliteapp;

import java.util.WeakHashMap;

public class Company {
    int id;
    String name;
    String webpage;
    String phone;
    String email;
    String detaill;
    String type;

    public Company(){
    }

    public Company(String name, String webpage, String phone, String email, String detaill, String type) {
        this.name = name;
        this.webpage = webpage;
        this.phone = phone;
        this.email = email;
        this.detaill = detaill;
        this.type = type;
    }

    public Company(int id, String name, String webpage, String phone, String email, String detaill, String type){
        this.id = id;
        this.name = name;
        this.webpage = webpage;
        this.email = email;
        this.phone = phone;
        this.detaill = detaill;
        this.type = type;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(){
        this.name=name;
    }
    public String getWebpage() {
        return webpage;
    }
    public void setWebpage(){
        this.webpage=webpage;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(){
        this.phone=phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(){
        this.email=email;
    }
    public String getDetaill() {
        return detaill;
    }
    public void setDetaill(){
        this.detaill=detaill;
    }
    public String getType() {
        return type;
    }
    public void setType(){
        this.name=type;
    }
}
