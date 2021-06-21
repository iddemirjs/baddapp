package com.idrisdemir.badapp.Entity;

import java.io.Serializable;

public class QuizResult implements Serializable {

    private String uuid;

    private int correctAnswerNumber;

    private int wrongAnswerNumber;

    private int elapsedTime;

    private int totalQuestionSize;

    private String playerName;

    private boolean isSuccess;

    private int profit;

    private String challengeUUID;

    public QuizResult() {
        this.correctAnswerNumber = 0;
        this.correctAnswerNumber = 0;
        this.isSuccess = false;
        this.challengeUUID = null;
    }

    public QuizResult(int totalQuestionSize, int correctNumber, int wrongAnswerNumber, String playerName, int profit) {
        this.correctAnswerNumber = correctNumber;
        this.wrongAnswerNumber = wrongAnswerNumber;
        this.playerName = playerName;
        this.profit = profit;
        this.totalQuestionSize = totalQuestionSize;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    public void setCorrectAnswerNumber(int correctAnswerNumber) { this.correctAnswerNumber = correctAnswerNumber; }

    public void increaseCorrectAnswerNumber() { this.correctAnswerNumber++; }

    public void increaseWrongAnswerNumber() { this.wrongAnswerNumber++; }

    public void addToProfit(int profit) { this.profit += profit; }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getWrongAnswerNumber() { return wrongAnswerNumber; }

    public void setWrongAnswerNumber(int wrongAnswerNumber) { this.wrongAnswerNumber = wrongAnswerNumber; }

    public int getElapsedTime() { return elapsedTime; }

    public void setElapsedTime(int elapsedTime) { this.elapsedTime = elapsedTime; }

    public int getTotalQuestionSize() { return totalQuestionSize; }

    public void setTotalQuestionSize(int totalQuestionSize) { this.totalQuestionSize = totalQuestionSize; }

    public boolean isSuccess() { return isSuccess; }

    public void setSuccess(boolean success) { isSuccess = success; }

    public String getChallengeUUID() { return challengeUUID; }

    public void setChallengeUUID(String challengeUUID) { this.challengeUUID = challengeUUID; }
}
