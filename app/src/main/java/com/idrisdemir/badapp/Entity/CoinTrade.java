package com.idrisdemir.badapp.Entity;

public class CoinTrade {

    private String uuid;

    private String transmitterId;

    private String receiverId;

    private int amount;

    public CoinTrade(){

    }

    public CoinTrade(String uuid, String transmitterId, String receiverId, int amount) {
        this.uuid = uuid;
        this.transmitterId = transmitterId;
        this.receiverId = receiverId;
        this.amount = amount;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTransmitterId() {
        return transmitterId;
    }

    public void setTransmitterId(String transmitterId) {
        this.transmitterId = transmitterId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
