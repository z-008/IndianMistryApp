package com.example.apple.indianmistry;

/**
 * Created by apple on 10/14/17.
 */

public class UserInfo {

    public String name;
    public String email;
    public String phone;

    public UserInfo()
    {

    }

    public UserInfo(String name, String email ,String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
