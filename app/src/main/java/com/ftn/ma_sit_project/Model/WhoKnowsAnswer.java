package com.ftn.ma_sit_project.Model;

public class WhoKnowsAnswer {

    private String text;
    private boolean isCorrect;


    public WhoKnowsAnswer() {
    }

    public WhoKnowsAnswer(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
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
