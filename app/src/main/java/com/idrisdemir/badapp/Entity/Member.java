package com.idrisdemir.badapp.Entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Member extends User {

    public Member(){

    };

    public Member(String username, String password, String gender,int cash, int experience) {
        super(username, password, gender);
        this.cash = cash;
        this.experience = experience;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int cash;

    public int experience;

}
