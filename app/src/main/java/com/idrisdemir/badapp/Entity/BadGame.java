package com.idrisdemir.badapp.Entity;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;


@IgnoreExtraProperties
public class BadGame implements Serializable {
    private String uuid;
    private String challengeOwnerUserName;
    private String categoryName;
    private int gameQuota;
    private int playedMatchSize;
    private int questionSize;
    private int joinPrice;

    public BadGame(){

    }

    public BadGame(String uuid, String challengeOwnerUserName, String categoryName, int gameQuota, int playedMatchSize, int questionSize, int joinPrice) {
        this.uuid = uuid;
        this.challengeOwnerUserName = challengeOwnerUserName;
        this.categoryName = categoryName;
        this.gameQuota = gameQuota;
        this.playedMatchSize = playedMatchSize;
        this.questionSize = questionSize;
        this.joinPrice = joinPrice;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getChallengeOwnerUserName() {
        return challengeOwnerUserName;
    }

    public void setChallengeOwnerUserName(String challengeOwnerUserName) {
        this.challengeOwnerUserName = challengeOwnerUserName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getGameQuota() {
        return gameQuota;
    }

    public void setGameQuota(int gameQuota) {
        this.gameQuota = gameQuota;
    }

    public int getPlayedMatchSize() {
        return playedMatchSize;
    }

    public void setPlayedMatchSize(int playedMatchSize) {
        this.playedMatchSize = playedMatchSize;
    }

    public void increasePlayedMatchSize(){
        this.playedMatchSize++;
    }

    public int getQuestionSize() {
        return questionSize;
    }

    public void setQuestionSize(int questionSize) {
        this.questionSize = questionSize;
    }

    public int getJoinPrice() {
        return joinPrice;
    }

    public void setJoinPrice(int joinPrice) {
        this.joinPrice = joinPrice;
    }
}
