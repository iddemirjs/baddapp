package com.idrisdemir.badapp.Entity;

public class BadGame {
    private String UUID;
    private String challengeOwnerUserName;
    private String categoryName;
    private int gameQuota;
    private int playedMatchSize;
    private int questionSize;
    private int joinPrice;

    public BadGame(){

    }

    public BadGame(String UUID, String challengeOwnerUserName, String categoryName, int gameQuota, int playedMatchSize, int questionSize, int joinPrice) {
        this.UUID = UUID;
        this.challengeOwnerUserName = challengeOwnerUserName;
        this.categoryName = categoryName;
        this.gameQuota = gameQuota;
        this.playedMatchSize = playedMatchSize;
        this.questionSize = questionSize;
        this.joinPrice = joinPrice;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
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
