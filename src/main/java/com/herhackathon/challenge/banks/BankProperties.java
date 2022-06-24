package com.herhackathon.challenge.banks;

import com.herhackathon.challenge.banks.commerz.oauth.CommerzOAuthProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class BankProperties {

    private final CommerzOAuthProperties commerzOAuthProperties;

    URL getLoginUrl(Bank bank) throws MalformedURLException {
        return switch (bank) {
            case COMMERZ -> new URL(commerzOAuthProperties.getLoginUrl());
            case CITI, SANTANDER -> new URL("");
            default -> throw new IllegalArgumentException("Unknown bank");
        };
    }

    public String getRedirectUri(Bank bank) {
        final String currentBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return switch (bank) {
            case COMMERZ -> commerzOAuthProperties.getRedirectUrl();
            case CITI, SANTANDER -> "";
            default -> throw new IllegalArgumentException("Unknown bank");
        };
    }

    public String getTokenUri(Bank bank) {
        return switch (bank) {
            case COMMERZ -> commerzOAuthProperties.getTokenUrl();
            case CITI, SANTANDER -> "";
            default -> throw new IllegalArgumentException("Unknown bank");
        };
    }
}
