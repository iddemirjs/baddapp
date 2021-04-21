package com.idrisdemir.badapp.Entity;

public class QuizResult {

    private String uuid;

    private QuizSchema quizSchema;

    private int correctNumber;

    private String playerId;

    private int challengeValue;

    public QuizResult() {

    }

    public QuizResult(QuizSchema quizSchema, int correctNumber, String playerId, int challengeValue) {
        this.quizSchema = quizSchema;
        this.correctNumber = correctNumber;
        this.playerId = playerId;
        this.challengeValue = challengeValue;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public QuizSchema getQuizSchema() {
        return quizSchema;
    }

    public void setQuizSchema(QuizSchema quizSchema) {
        this.quizSchema = quizSchema;
    }

    public int getCorrectNumber() {
        return correctNumber;
    }

    public void setCorrectNumber(int correctNumber) {
        this.correctNumber = correctNumber;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getChallengeValue() {
        return challengeValue;
    }

    public void setChallengeValue(int challengeValue) {
        this.challengeValue = challengeValue;
    }
}
