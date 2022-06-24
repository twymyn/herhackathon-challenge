package com.herhackathon.challenge.banks;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BankConverter implements Converter<String, Bank> {

    @Override
    public Bank convert(String value) {
        return Bank.valueOf(value.toUpperCase());
    }
}