package com.lazetic.pollingapp.objects;

public class User {
    private String email;
    private String password;
    private String name;
    private String location_name;
    // TODO location


    @Override
    public String toString() {
        return "'"+name + "','" + email + "','" + password+"'";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public User(String email, String password, String name, String location_name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.location_name = location_name;
    }

    public User(String name, String email, String password) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User() {
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
}
