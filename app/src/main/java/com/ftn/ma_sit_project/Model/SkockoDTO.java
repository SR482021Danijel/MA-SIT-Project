package com.ftn.ma_sit_project.Model;

import com.google.gson.annotations.SerializedName;

public class SkockoDTO {

    @SerializedName("firstTag")
    String firstTag;
    @SerializedName("secondTag")
    String secondTag;
    @SerializedName("thirdTag")
    String thirdTag;
    @SerializedName("fourthTag")
    String fourthTag;

    public SkockoDTO(String firstTag, String secondTag, String thirdTag, String fourthTag) {
        this.firstTag = firstTag;
        this.secondTag = secondTag;
        this.thirdTag = thirdTag;
        this.fourthTag = fourthTag;
    }

    public SkockoDTO() {
    }

    public String getFirstTag() {
        return firstTag;
    }

    public void setFirstTag(String firstTag) {
        this.firstTag = firstTag;
    }

    public String getSecondTag() {
        return secondTag;
    }

    public void setSecondTag(String secondTag) {
        this.secondTag = secondTag;
    }

    public String getThirdTag() {
        return thirdTag;
    }

    public void setThirdTag(String thirdTag) {
        this.thirdTag = thirdTag;
    }

    public String getFourthTag() {
        return fourthTag;
    }

    public void setFourthTag(String fourthTag) {
        this.fourthTag = fourthTag;
    }
}
