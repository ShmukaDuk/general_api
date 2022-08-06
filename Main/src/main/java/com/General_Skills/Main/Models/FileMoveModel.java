package com.General_Skills.Main.Models;

public class FileMoveModel {
    String username;
    String token;
    String location;
    String fileName;






    public FileMoveModel() { super(); }

    public FileMoveModel(String username, String token, String location, String fileName){
        this.username = username;
        this.token = token;
        this.location = location;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getLocation(){return location;}

    public void  setLocation(String location){this.location = location;}



    @Override
    public String toString() {
        return "CreateUserModel[" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", location='" + location + '\'' +
                ", fileName='" + fileName + '\'' +
                ']';
    }
}