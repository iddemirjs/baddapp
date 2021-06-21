package com.idrisdemir.badapp.SystemEngine;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.idrisdemir.badapp.Entity.BadGame;
import com.idrisdemir.badapp.Entity.CoinTrade;
import com.idrisdemir.badapp.Entity.EnergyTrade;
import com.idrisdemir.badapp.Entity.Member;
import com.idrisdemir.badapp.Entity.QuizResult;
import com.idrisdemir.badapp.Entity.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class GameManagement {
    // TODO : Kullanıcıya tecrübe ekle.
    // TODO : Kullanıcıdan enerji düşür.
    // TODO : Badgame oynanan maç sayısı artır.
    // TODO : Kullanıcının normal Game Ödüllünü Dağıt.
    // TODO : Kullanıcının Badgame ödülünü dağıt.

    private QuizResult gameResult;
    private BadGame badGame;
    private DatabaseReference databaseReference;

    public GameManagement() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void gameStarter() {
        this.decreaseEnergy();
        if (this.gameResult.getChallengeUUID() != null) this.decreaseQuota();

    }

    private void decreaseQuota() {
        Query query = databaseReference.child("badgames").child(this.gameResult.getChallengeUUID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ss : snapshot.getChildren()) {
                    badGame = ss.getValue(BadGame.class);
                }
                badGame.increasePlayedMatchSize();
                databaseReference.child("badgames").child(gameResult.getChallengeUUID()).setValue(badGame);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void gameFinisher() {
        if (gameResult.isSuccess()) {
            this.increaseEnergy();
            this.distributePrize();
            this.distributeExperience();
        }
    }

    private void distributeExperience() {
        int experienceProfit, minProfit, maxProfit;
        Random random = new Random();
        if (this.gameResult.getChallengeUUID() != null) {
            minProfit = 15;
            maxProfit = 15;
        } else {
            minProfit = 3;
            maxProfit = 5;
        }
        experienceProfit = random.nextInt(maxProfit) + minProfit;
        Query query = databaseReference.child("users")
                .orderByChild("username").equalTo(this.gameResult.getPlayerName());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Member member = new Member();
                for (DataSnapshot ss : snapshot.getChildren()) {
                    member = ss.getValue(Member.class);
                }
                member.setExperience(member.getExperience() + experienceProfit);
                databaseReference.child("users").child(member.getUuid()).setValue(member);
                UserManagement userManagement = new UserManagement();
                userManagement.levelUppControl(member.getUsername());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void startChallengeFinisherProcedures() {
        Query query = databaseReference.child("quizResults")
                .orderByChild("challengeUUID").equalTo(this.gameResult.getUuid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<QuizResult> quizResults = new ArrayList<QuizResult>();
                for (DataSnapshot ss : snapshot.getChildren()) {
                    quizResults.add(ss.getValue(QuizResult.class));
                }
                controlOwnerScore(quizResults);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public void controlOwnerScore(ArrayList<QuizResult> quizResults) {
        if (this.gameResult.getPlayerName().equalsIgnoreCase(badGame.getChallengeOwnerUserName())) {
            // TODO : Dont make something
        } else {
            QuizResult ownerResults = null;
            for (QuizResult tempResult : quizResults) {
                if (tempResult.getPlayerName().equalsIgnoreCase(badGame.getChallengeOwnerUserName())) {
                    ownerResults = tempResult;
                    break;
                }
            }
            if (ownerResults != null) {
                if ((!ownerResults.isSuccess()) || ownerResults.getCorrectAnswerNumber() < this.gameResult.getCorrectAnswerNumber()) {
                    CoinTrade coinTrade = new CoinTrade();
                    coinTrade.setUuid(UUID.randomUUID().toString());
                    coinTrade.setAmount(this.badGame.getJoinPrice() * 2);
                    coinTrade.setReceiverUserName(this.gameResult.getPlayerName());
                    coinTrade.setTransmitterUserName("BadAppCash");

                    this.databaseReference.child("coinTrades").child(coinTrade.getUuid()).setValue(coinTrade);
                }else if ((!gameResult.isSuccess()) || ownerResults.getCorrectAnswerNumber() > this.gameResult.getCorrectAnswerNumber()){
                    CoinTrade coinTrade = new CoinTrade();
                    coinTrade.setUuid(UUID.randomUUID().toString());
                    coinTrade.setAmount(this.badGame.getJoinPrice() * 2);
                    coinTrade.setReceiverUserName(this.badGame.getChallengeOwnerUserName());
                    coinTrade.setTransmitterUserName("BadAppCash");
                }
            }
        }
    }


    private void distributePrize() {
        if (this.gameResult.getChallengeUUID() != null) {
            this.startChallengeFinisherProcedures();
            return;
        } else {
            CoinTrade coinTrade = new CoinTrade();
            coinTrade.setUuid(UUID.randomUUID().toString());
            coinTrade.setAmount(this.gameResult.getProfit());
            if (this.gameResult.isSuccess()) {
                coinTrade.setReceiverUserName(this.gameResult.getPlayerName());
                coinTrade.setTransmitterUserName("BadAppCash");
            } else {
                coinTrade.setTransmitterUserName(this.gameResult.getPlayerName());
                coinTrade.setReceiverUserName("BadAppCash");
            }
            this.databaseReference.child("coinTrades").child(coinTrade.getUuid()).setValue(coinTrade);
        }
    }

    private void increaseEnergy() {
        EnergyTrade energyTrade = new EnergyTrade();
        if (this.gameResult.getChallengeUUID() == null) energyTrade.setEnergyPiece(+2);
        else energyTrade.setEnergyPiece(+2);
        energyTrade.setTradeType("profit");
        energyTrade.setUsername(this.gameResult.getPlayerName());
        energyTrade.setUuid(UUID.randomUUID().toString());
        this.databaseReference.child("energyTrades").child(energyTrade.getUuid()).setValue(energyTrade);
    }

    private void decreaseEnergy() {
        EnergyTrade energyTrade = new EnergyTrade();
        if (this.gameResult.getChallengeUUID() == null) energyTrade.setEnergyPiece(-1);
        else energyTrade.setEnergyPiece(-2);
        energyTrade.setTradeType("loss");
        energyTrade.setUsername(this.gameResult.getPlayerName());
        energyTrade.setUuid(UUID.randomUUID().toString());
        this.databaseReference.child("energyTrades").child(energyTrade.getUuid()).setValue(energyTrade);
    }

    public QuizResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(QuizResult gameResult) {
        this.gameResult = gameResult;
    }
}
