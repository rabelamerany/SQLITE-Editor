package com.example.lenovo.androidmyadmin.model;

public class User {
    private int id;

    private String email;
    private String password;
    private String type;


    public User(int id)
    {
        this.id=id;
    }
    public User()
    {
    }
    public User(int id, String email, String password, String type)
    {
        this.id=id;
        this.email=email;
        this.password=password;

        this.type=type;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
