package com.mdwikuntobayu.androidbasic.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wgs-lap148 on 12/3/15.
 */
public class Users {
    @SerializedName("users")
    public List<UserItem> users;

    public List<UserItem> getUsers() {
        return users;
    }

    public void setUsers(List<UserItem> users) {
        this.users = users;
    }

    public Users(List<UserItem> users) {
        this.users = users;
    }

    public class UserItem{
        private int id;
        private String email;
        private String password;
        private String token_auth;
        private String created_at;
        private String updated_at;

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
}

