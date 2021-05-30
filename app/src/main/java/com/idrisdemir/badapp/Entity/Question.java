package com.idrisdemir.badapp.Entity;

public class Question {
    private String question;
    private int time;
    private String[] options;
    private int categoryId;
    private int value;


    public Question(String question, int time, String[] options, int categoryId, int value) {
        this.question = question;
        this.time = time;
        this.options=new String[options.length];
        for (int i = 0; i < options.length; i++) {

            this.options[i] = options[i];
        }
        this.categoryId = categoryId;
        this.value = value;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String[] getOptions() {
        return options;
    }
    public String getOptions(int a) {
        return options[a];
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getTime() {
        return time;
    }
}
