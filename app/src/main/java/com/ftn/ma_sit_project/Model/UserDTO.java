package com.ftn.ma_sit_project.Model;

import com.google.gson.annotations.SerializedName;

public class UserDTO {

    @SerializedName("username")
    private String username;

    @SerializedName("points")
    private int points;

    public UserDTO(String username, int points) {
        this.username = username;
        this.points = points;
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
}
