package com.idrisdemir.badapp.Entity;

public class QuizSchema {

    private String uuid;

    private String title;

    private QuestionGroup[] questionGroups;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public QuestionGroup[] getQuestionGroups() {
        return questionGroups;
    }

    public void setQuestionGroups(QuestionGroup[] questionGroups) {
        this.questionGroups = questionGroups;
    }
}
