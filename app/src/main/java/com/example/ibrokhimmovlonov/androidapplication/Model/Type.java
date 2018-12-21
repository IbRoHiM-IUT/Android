package com.example.ibrokhimmovlonov.androidapplication.Model;

public class Type {
    private String Name;
    private String Image;

    public Type() {
    }

    public Type(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
