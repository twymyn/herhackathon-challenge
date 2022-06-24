package com.herhackathon.challenge.banks;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;

@Component
@Getter
@Setter
public class BankProperties {

    @Value("${spring.security.oauth2.client.provider.commerz.authorization-uri}")
    private String commerzLoginUrl;

    @Value("${spring.security.oauth2.client.provider.commerz.token-uri}")
    private String commerzTokenUrl;

    @Value("${spring.security.oauth2.client.registration.commerz.client-id}")
    private String commerzClientId;

    @Value("${spring.security.oauth2.client.registration.commerz.client-secret}")
    private String commerzClientSecret;

    @Value("${spring.security.oauth2.client.registration.commerz.redirect-uri}")
    private String commerzRedirectUri;

    URL getLoginUrl(Bank bank) throws MalformedURLException {
        switch (bank) {
            case COMMERZ:
                String finalUrl = commerzLoginUrl +
                        "?response_type=code" +
                        "&client_id=" + commerzClientId +
                        "&redirect_uri=" + getRedirectUri(bank);
                return new URL(finalUrl);
            case CITI, SANTANDER:
                return new URL("");
            default:
                throw new IllegalArgumentException("Unknown bank");
        }
    }

    public String getRedirectUri(Bank bank) {
        final String currentBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return switch (bank) {
            case COMMERZ -> commerzRedirectUri.replace("{baseUrl}", currentBaseUrl);
            case CITI, SANTANDER -> "";
            default -> throw new IllegalArgumentException("Unknown bank");
        };
    }

    public String getTokenUri(Bank bank) {
        return switch (bank) {
            case COMMERZ -> commerzTokenUrl;
            case CITI, SANTANDER -> "";
            default -> throw new IllegalArgumentException("Unknown bank");
        };
    }
}
