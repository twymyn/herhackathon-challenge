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
class BankProperties {

    @Value("${spring.security.oauth2.client.provider.commerz.authorization-uri}")
    private String commerzLoginUrl;

    @Value("${spring.security.oauth2.client.registration.commerz.client-id}")
    private String commerzClientId;

    @Value("${spring.security.oauth2.client.registration.commerz.redirect-uri}")
    private String commerzRedirectUri;

    URL getLoginUrl(Bank bank) throws MalformedURLException {
        final String currentBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        switch (bank) {
            case COMMERZ:
                String baseUrl = commerzRedirectUri.replace("{baseUrl}", currentBaseUrl);
                String finalUrl = new StringBuilder(commerzLoginUrl)
                        .append("?response_type=code")
                        .append("&client_id=").append(commerzClientId)
                        .append("&redirect_uri=").append(baseUrl)
                        .toString();
                return new URL(finalUrl);
            case CITI, SANTANDER:
                return new URL("");
            default:
                throw new IllegalArgumentException("Unknown bank");
        }
    }
}
