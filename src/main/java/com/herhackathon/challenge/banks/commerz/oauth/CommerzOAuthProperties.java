package com.herhackathon.challenge.banks.commerz.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
@Getter
@Setter
public class CommerzOAuthProperties {

    @Value("${spring.security.oauth2.client.provider.commerz.authorization-uri}")
    private String loginUrl;

    @Value("${spring.security.oauth2.client.provider.commerz.token-uri}")
    private String tokenUrl;

    @Value("${spring.security.oauth2.client.registration.commerz.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.commerz.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.commerz.redirect-uri}")
    private String redirectUri;

    public String getLoginUrl() {
        return loginUrl +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + getRedirectUrl();
    }

    public String getRedirectUrl() {
        final String currentBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return redirectUri.replace("{baseUrl}", currentBaseUrl);
    }
}
