package com.flock.bankManager.models;

public class TransactionReq {
    private String email;
    private int amount;

    public TransactionReq() {
    }

    public TransactionReq(String email, int amount) {
        this.email = email;
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
