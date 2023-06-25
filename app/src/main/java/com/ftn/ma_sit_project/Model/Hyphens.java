package com.ftn.ma_sit_project.Model;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.google.gson.annotations.SerializedName;

public class Hyphens {
    @SerializedName("id")
    private int id;
    @SerializedName("text")
    private String text;
    @SerializedName("color")
    private int color;

    public Hyphens(int id, String text, int color){
        this.id = id;
        this.text = text;
        this.color = color;
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
}
