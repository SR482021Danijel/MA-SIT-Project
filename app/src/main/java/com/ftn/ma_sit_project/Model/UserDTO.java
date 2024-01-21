package com.ftn.ma_sit_project.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserDTO {

    @SerializedName("username")
    private String username;

    @SerializedName("points")
    private int points;

    @SerializedName("turnNumber")
    private double turnNumber;

    @SerializedName("roundList")
    private ArrayList<Integer> roundList;

    @SerializedName("isOpponentCorrect")
    private Boolean isOpponentCorrect;

    @SerializedName("opponentNumber")
    private double opponentNumber;

    public UserDTO(String username, int points, double turnNumber) {
        this.username = username;
        this.points = points;
        this.turnNumber = turnNumber;
    }

    public UserDTO(ArrayList<Integer> roundList) {
        this.roundList = roundList;
    }

    public UserDTO(String username) {
        this.username = username;
    }

    public UserDTO(String username, int points, Boolean isOpponentCorrect, double opponentNumber) {
        this.username = username;
        this.points = points;
        this.isOpponentCorrect = isOpponentCorrect;
        this.opponentNumber = opponentNumber;
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

    public ArrayList<Integer> getRoundList() {
        return roundList;
    }

    public void setRoundList(ArrayList<Integer> roundList) {
        this.roundList = roundList;
    }

    public Boolean isOpponentCorrect() {
        return isOpponentCorrect;
    }

    public void setOpponentCorrect(Boolean opponentCorrect) {
        isOpponentCorrect = opponentCorrect;
    }

    public double getOpponentNumber() {
        return opponentNumber;
    }

    public void setOpponentNumber(double opponentNumber) {
        this.opponentNumber = opponentNumber;
    }
}
