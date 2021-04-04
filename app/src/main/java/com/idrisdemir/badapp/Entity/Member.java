package com.idrisdemir.badapp.Entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Member extends User {

    public Member() {

    }

    public Member(int cash, int experience) {
        this.cash = cash;
        this.experience = experience;
    }

    public Member(String username, String password, int cash, int experience) {
        super(username, password);
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
