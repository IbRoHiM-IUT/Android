package com.example.ibrokhimmovlonov.androidapplication.Model;

public class User {
    private String Name;
    private String Password;
    private String type;
    private String Phone;


    public User() {
    }

    public User(String name, String password, String type) {
        this.Name = name;
        this.Password = password;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
