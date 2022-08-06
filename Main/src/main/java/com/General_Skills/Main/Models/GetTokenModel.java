package com.General_Skills.Main.Models;

public class GetTokenModel {
    String username;
    String password;

    public GetTokenModel() { super(); }

    public GetTokenModel(String username, String password) {
        this.username = username;
        this.password = password;
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



    @Override
    public String toString() {
        return "GetTokenModel[" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ']';
    }
}
