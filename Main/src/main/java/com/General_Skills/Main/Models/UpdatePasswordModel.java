package com.General_Skills.Main.Models;

public class UpdatePasswordModel {
    String username;
    String password;
    String newPassword;
    String newMilk;


    public UpdatePasswordModel() { super(); }

    public UpdatePasswordModel(String username, String password, String newPassword) {
        this.username = username;
        this.password = password;
        this.newPassword = newPassword;
        this.newMilk = newMilk;
    }

    public String getNewMilk() {
        return newMilk;
    }

    public void setNewMilk(String newMilk) {
        this.newMilk = newMilk;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "UpdatePasswordModel[" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", newMilk='" + newMilk + '\'' +
                ']';
    }
}
