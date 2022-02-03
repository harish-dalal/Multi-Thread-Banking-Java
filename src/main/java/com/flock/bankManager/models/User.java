package com.flock.bankManager.models;


public class User {
    private String email;
    private String name;
    private int balance;

    public User() {
    }

    public User(String email, String name, int balance) {
        this.email = email;
        this.name = name;
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
