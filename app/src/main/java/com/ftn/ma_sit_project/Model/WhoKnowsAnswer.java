package com.ftn.ma_sit_project.Model;

public class WhoKnowsAnswer {

    private String username;
    private String text;
    private boolean isCorrect;


    public WhoKnowsAnswer() {
    }

    public WhoKnowsAnswer(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public WhoKnowsAnswer(String username, String text, boolean isCorrect) {
        this.username = username;
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
