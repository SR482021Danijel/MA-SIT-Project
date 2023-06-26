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

    public Hyphens(int id, String text, int color, String userName){
        this.id = id;
        this.text = text;
        this.color = color;
        this.userName = userName;
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

    @Override
    public String toString() {
        return "Hyphens{" +
                "userName='" + userName + '\'' +
                ", id=" + id +
                ", text='" + text + '\'' +
                ", color=" + color +
                '}';
    }
}
