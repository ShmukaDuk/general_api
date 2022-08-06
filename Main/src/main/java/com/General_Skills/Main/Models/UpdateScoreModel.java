package com.General_Skills.Main.Models;

public class UpdateScoreModel {
    String username;
    String highScore;
    String highScoreName;

    public UpdateScoreModel() { super(); }



    public UpdateScoreModel(String username, String highScore) {
        this.username = username;
        this.highScore = highScore;
    }



    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getHighScore() {return highScore;}

    public void setHighScore(String highScore)
    {
        this.highScore = highScore;
    }

    public String getHighScoreName() {        return highScoreName;    }

    public void setHighScoreName(String highScoreName) {        this.highScoreName = highScoreName;    }

    @Override
    public String toString() {
        return "UpdateScoreModel[" +
                "username='" + username + '\'' +
                ", highScore='" + highScore + '\'' +
                ", highScoreName='" + highScoreName + '\'' +
                ']';
    }
}
