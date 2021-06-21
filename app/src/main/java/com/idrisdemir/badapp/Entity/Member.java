package com.idrisdemir.badapp.Entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Member extends User {

    private int experience;

    private int level;

    private String email;

    public Member() {

        this.experience = 0;
        this.level = 1;
    }

    public Member(String username, String email, String password, String gender, int experience, int level) {
        super(username, password, gender);
        this.experience = experience;
        this.email = email;
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
