package com.General_Skills.Main.Models;

public class CreateUserModel {
    String username;
    String password;
    String highScore;

    public CreateUserModel() { super(); }

    public CreateUserModel(String username, String password, String newPassword, String highScore) {
        this.username = username;
        this.password = password;
        this.highScore = highScore;
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

    public String getHighScore(){return highScore;}

    public void  setHighScore(String highScore){this.highScore = highScore;}



    @Override
    public String toString() {
        return "CreateUserModel[" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", highScore='" + highScore + '\'' +
                ']';
    }
}
