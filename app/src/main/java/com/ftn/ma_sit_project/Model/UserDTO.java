package com.ftn.ma_sit_project.Model;

import com.google.gson.annotations.SerializedName;

public class UserDTO {

    @SerializedName("username")
    private String username;

    @SerializedName("points")
    private int points;

    @SerializedName("turnNumber")
    private double turnNumber;

    public UserDTO(String username, int points, double turnNumber) {
        this.username = username;
        this.points = points;
        this.turnNumber = turnNumber;
    }

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(double turnNumber) {
        this.turnNumber = turnNumber;
    }
}
