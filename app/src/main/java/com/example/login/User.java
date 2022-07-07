package com.example.login;

public class User {
    public String fullName, age, email, weight, height;

    public User(){

    }

    public User(String fullName, String age, String email, String weight, String height)
    {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.weight = weight;
        this.height = height;
    }
}
