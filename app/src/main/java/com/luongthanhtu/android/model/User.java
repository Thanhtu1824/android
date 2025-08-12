package com.luongthanhtu.android.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("email")
    private String email;

    @SerializedName("numphone")
    private String numphone;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public User(String email, String numphone, String username, String password) {
        this.email = email;
        this.numphone = numphone;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumphone() {
        return numphone;
    }
    public void setNumphone(String numphone) {
        this.numphone = numphone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
