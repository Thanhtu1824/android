package com.luongthanhtu.android.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id") // Nếu API trả id thì khai báo để Gson map
    private String id;

    @SerializedName("email")
    private String email;

    @SerializedName("numphone")
    private String numphone;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    // 🔹 Constructor rỗng (bắt buộc để Gson parse JSON)
    public User() {
    }

    // 🔹 Constructor 4 tham số (dùng khi đăng ký, chưa có id)
    public User(String email, String numphone, String username, String password) {
        this.email = email;
        this.numphone = numphone;
        this.username = username;
        this.password = password;
    }

    // 🔹 Constructor đầy đủ (dùng khi muốn set thủ công tất cả)
    public User(String id, String email, String numphone, String username, String password) {
        this.id = id;
        this.email = email;
        this.numphone = numphone;
        this.username = username;
        this.password = password;
    }

    // Getter & Setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
