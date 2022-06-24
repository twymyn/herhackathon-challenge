package com.herhackathon.challenge.banks;

public enum Bank {
    COMMERZ("Commerzbank"), CITI("citi"), SANTANDER("Santander");

    public final String fullName;

    Bank(String fullName) {
        this.fullName = fullName;
    }
}
