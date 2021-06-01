package com.idrisdemir.badapp.Entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Question {
    private String question;
    private int time;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String categoryName;
    private int value;

    public Question(){

    }

    public Question(String question, int time, String[] options, int value,String categoryName) {
        this.question = question;
        this.time = time;
        this.option1 = options[0];
        this.option2 = options[1];
        this.option3 = options[2];
        this.option4 = options[3];
        this.value = value;
        this.categoryName = categoryName;
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


    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
