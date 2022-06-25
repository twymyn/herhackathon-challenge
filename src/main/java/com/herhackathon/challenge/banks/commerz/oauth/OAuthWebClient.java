package com.herhackathon.challenge.banks.commerz.oauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Calendar;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthWebClient {

    private final WebClient.Builder webClientBuilder;

    private final CommerzOAuthProperties commerzOAuthProperties;

    @Getter
    private OAuthResponse oAuthResponse;

    @Getter
    private Date expiryDate;

    public void setOAuthResponse(OAuthResponse oAuthResponse) {
        this.oAuthResponse = oAuthResponse;
        if (oAuthResponse != null) {
            this.expiryDate = calculateExpiryDate(oAuthResponse.getExpiresIn());
        }
    }

    private Date calculateExpiryDate(int expiresInSeconds) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (expiresInSeconds * 1000L));
        log.info("Received token at {}, expires at {}", now, expiryDate);
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getAccessToken() {
        if (oAuthResponse != null) {
            return oAuthResponse.getAccessToken();
        } else {
            throw new IllegalStateException("No access token found");
        }
    }

    // TODO could be handled better with expiry date or refresh token
    // token needs to be stored per session/user
    @Scheduled(cron = "0 * * * * *")
    public void resetStoredAccessToken() {
        Calendar now = Calendar.getInstance();
        if (now.after(getExpiryDate())) {
            log.info("clearing the stored access token");
            setOAuthResponse(null);
            setExpiryDate(null);
        }
    }

    /**
     * Request a new access token based on the refresh token.
     *
     * @param refreshToken Valid refresh token.
     * @return OAuth response with a refreshed access token.
     */
    public OAuthResponse refreshAccessToken(String refreshToken) {
        return requestAccessToken(null, refreshToken, AuthorizationGrantType.REFRESH_TOKEN, null);
    }

    /**
     * Request a access token with a code, which was return form a oauth client login.
     *
     * @param code Valid code return from an oauth login.
     * @param redirectUri Valid redirectUri used by your oauth credentials.
     * @return OAuth response with a access token.
     */
    OAuthResponse requestAccessTokenWithAuthCode(String code, String redirectUri) {
        return requestAccessToken(code, null, AuthorizationGrantType.AUTHORIZATION_CODE, redirectUri);
    }


    /**
     * Request a access token only based on the client id and secret.
     *
     * @return OAuth response with a access token.
     */
    public OAuthResponse requestAccessTokenWithClientCredentials() {
        return requestAccessToken(null, null, AuthorizationGrantType.CLIENT_CREDENTIALS, null);
    }

    private OAuthResponse requestAccessToken(String code, String refreshToken, AuthorizationGrantType grantType, String redirectUri) {
        WebClient client = webClientBuilder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        return client.post()                                                        // Use HTTP verb POST
                .uri(commerzOAuthProperties.getTokenUrl())                                                           // Use endpoint Authorization server
                .body(createBody(code, refreshToken, grantType, redirectUri))
                .retrieve()
                .bodyToMono(OAuthResponse.class)                                    // Map JSON response to a Java object
                .onErrorMap(e -> new RuntimeException("Error requesting access token.", e))
                .block();
    }

    private BodyInserters.FormInserter<String> createBody(String code, String refreshToken, AuthorizationGrantType grantType, String redirectUri) {

        BodyInserters.FormInserter<String> body = BodyInserters
                .fromFormData("grant_type", grantType.getValue())  // Set form data grant_type
                .with("client_id", commerzOAuthProperties.getClientId())             // Set form data client_id
                .with("client_secret", commerzOAuthProperties.getClientSecret());          // Set form data client_secret

        if (!StringUtils.isEmpty(code)) {                                   // Set optional form data code
            body = body.with("code", code);
        }

        if (!StringUtils.isEmpty(refreshToken)) {                           // Set optional form data refresh_token
            body = body.with("refresh_token", refreshToken);
        }

        if (!StringUtils.isEmpty(redirectUri)) {                               // Set optional form data redirectUri
            body = body.with("redirect_uri", redirectUri);
        }

        return body;
    }
}
