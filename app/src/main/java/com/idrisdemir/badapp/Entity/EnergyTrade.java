package com.idrisdemir.badapp.Entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class EnergyTrade {

    private String uuid;
    private String username;
    private int energyPiece;
    private String tradeType;

    public EnergyTrade(){

    }

    public EnergyTrade(String uuid, String username, int energyPiece, String tradeType) {
        this.uuid = uuid;
        this.username = username;
        this.energyPiece = energyPiece;
        this.tradeType = tradeType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getEnergyPiece() {
        return energyPiece;
    }

    public void setEnergyPiece(int energyPiece) {
        this.energyPiece = energyPiece;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
