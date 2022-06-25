package com.herhackathon.challenge.banks;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
public enum Bank {
    COMMERZ("Commerzbank"), CITI("citi"), SANTANDER("Santander");

    public final String fullName;

    Bank(String fullName) {
        this.fullName = fullName;
    }

    public static Bank findByName(String name) {
        try {
            return Bank.valueOf(name.toUpperCase());
        } catch (Exception e) {
            log.warn("Given {} is not a Bank enum name - {}", name, e.getMessage());
        }
        return findBankByFullName(name);
    }

    public static Bank findBankByFullName(String fullName) {
        Optional<Bank> found = Arrays.stream(values())
                .filter(bank -> bank.fullName.equalsIgnoreCase(fullName))
                .findFirst();
        if (found.isPresent()) {
            return found.get();
        } else {
            throw new IllegalArgumentException("Unknown bank " + fullName);
        }
    }
}
