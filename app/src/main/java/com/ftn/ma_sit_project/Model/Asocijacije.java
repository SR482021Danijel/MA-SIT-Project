package com.ftn.ma_sit_project.Model;

import com.google.gson.annotations.SerializedName;

public class Asocijacije {

    @SerializedName("id")
    private int id;
    @SerializedName("text")
    private String text;
    @SerializedName("colunnName")
    private String columnName;
    @SerializedName("userName")
    private String userName;


    public Asocijacije(int id, String text, String columnName, String userName) {
        this.id = id;
        this.text = text;
        this.columnName = columnName;
        this.userName = userName;
    }

    public Asocijacije() {
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Asocijacije{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", columnName='" + columnName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
