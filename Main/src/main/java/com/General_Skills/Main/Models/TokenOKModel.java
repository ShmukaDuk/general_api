package com.General_Skills.Main.Models;

public class TokenOKModel {
    String username;
    String token;

    public TokenOKModel() { super(); }

    public TokenOKModel(String username, String password) {
        this.username = username;
        this.token = password;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    @Override
    public String toString() {
        return "tokenOKModel[" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                ']';
    }
}
