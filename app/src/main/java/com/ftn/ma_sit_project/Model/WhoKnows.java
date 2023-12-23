package com.ftn.ma_sit_project.Model;

import java.util.ArrayList;
import java.util.List;

public class WhoKnows {

    private String question;
    private List<WhoKnowsAnswer> answers;

    public WhoKnows() {
    }

    public WhoKnows(String question, List<WhoKnowsAnswer> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<WhoKnowsAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<WhoKnowsAnswer> answers) {
        this.answers = answers;
    }
}
