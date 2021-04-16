package com.idrisdemir.badapp.Entity;

public class QuestionGroup {
    private String categoryId;

    private int questionCount;

    private int questionLevels;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getCategoryLevel() {
        return questionLevels;
    }

    public void setCategoryLevel(int questionLevels) {
        this.questionLevels = questionLevels;
    }
}
