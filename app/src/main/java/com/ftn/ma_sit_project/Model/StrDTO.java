package com.ftn.ma_sit_project.Model;

import com.google.gson.annotations.SerializedName;

public class StrDTO {

    @SerializedName("columnName")
    private String columnName;
    @SerializedName("text")
    private String text;
    @SerializedName("userName")
    private String userName;

    public StrDTO(String columnName, String text, String userName) {
        this.columnName = columnName;
        this.text = text;
        this.userName = userName;
    }

    public StrDTO() {
    }

    public String getText() {
        return text;
    }

    public String getUserName() {
        return userName;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return "StrDTO{" +
                "text='" + text + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
