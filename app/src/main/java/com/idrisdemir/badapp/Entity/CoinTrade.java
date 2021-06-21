package com.idrisdemir.badapp.Entity;

public class CoinTrade {

    private String uuid;

    private String transmitterUserName;

    private String receiverUserName;

    private int amount;

    public CoinTrade(){

    }

    public CoinTrade(String uuid, String transmitterUserName, String receiverId, int amount) {
        this.uuid = uuid;
        this.transmitterUserName = transmitterUserName;
        this.receiverUserName = receiverId;
        this.amount = amount;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTransmitterUserName() {
        return transmitterUserName;
    }

    public void setTransmitterUserName(String transmitterUserName) {
        this.transmitterUserName = transmitterUserName;
    }

    public String getReceiverUserName() {
        return receiverUserName;
    }

    public void setReceiverUserName(String receiverUserName) {
        this.receiverUserName = receiverUserName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
