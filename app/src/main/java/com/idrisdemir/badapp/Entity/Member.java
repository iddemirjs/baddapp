package com.idrisdemir.badapp.Entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Member extends User {

    private int experience;

    private int level;

    public Member(){

    };

    public Member(String username, String password, String gender, int experience) {
        super(username, password, gender);
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

}
