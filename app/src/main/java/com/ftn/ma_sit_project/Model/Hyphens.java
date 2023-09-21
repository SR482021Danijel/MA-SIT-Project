package com.ftn.ma_sit_project.Model;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.google.gson.annotations.SerializedName;

public class Hyphens {
    @SerializedName("userName")
    private String userName;
    @SerializedName("id")
    private int id;
    @SerializedName("text")
    private String text;
    @SerializedName("color")
    private int color;

    @SerializedName("turn")
    private boolean isTurnChanged;

    @SerializedName("start")
    private boolean isStart;

    @SerializedName("counter")
    private int counterIsMyTurnOver;


    public Hyphens(int id, String text, int color, String userName, boolean isTurnChanged, boolean isStart, int counterIsMyTurnOver){
        this.id = id;
        this.text = text;
        this.color = color;
        this.userName = userName;
        this.isTurnChanged = isTurnChanged;
        this.isStart = isStart;
        this.counterIsMyTurnOver = counterIsMyTurnOver;
    }

    public Hyphens(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isTurnChanged() {
        return isTurnChanged;
    }

    public void setTurnChanged(boolean turnChanged) {
        isTurnChanged = turnChanged;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public int getCounterIsMyTurnOver() {
        return counterIsMyTurnOver;
    }

    public void setCounterIsMyTurnOver(int counterIsMyTurnOver) {
        this.counterIsMyTurnOver = counterIsMyTurnOver;
    }


    @Override
    public String toString() {
        return "Hyphens{" +
                "userName='" + userName + '\'' +
                ", id=" + id +
                ", text='" + text + '\'' +
                ", color=" + color +
                ", isTurnChanged=" + isTurnChanged +
                ", isStart=" + isStart +
                ", counterIsMyTurnOver=" + counterIsMyTurnOver +
                '}';
    }
}
