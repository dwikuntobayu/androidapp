package com.mdwikuntobayu.androidbasic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class User {
//    List<User> users;
//
//    public List<User> getUserList() {
//        return users;
//    }
//
//    public void setUserList(List<User> userList) {
//        this.users = userList;
//    }

    private int id;
    private String email;
    private String password;
    private String token_auth;
    private String created_at;
    private String updated_at;

    public User(String email, String password, String token_auth) {
        this.email = email;
        this.password = password;
        this.token_auth = token_auth;
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

    public String getToken_auth() {
        return token_auth;
    }

    public void setToken_auth(String token_auth) {
        this.token_auth = token_auth;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

}
