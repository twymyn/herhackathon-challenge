package com.herhackathon.challenge.banks.commerz.securities;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class SecuritiesWebClient {

    private final WebClient.Builder webClientBuilder;

    public SecurityAccounts getAllAccounts(String accessToken) {
        WebClient client = webClientBuilder
                .baseUrl("https://api-sandbox.commerzbank.com/securities-api/v3")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return client.get()
                .uri("/security-accounts")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(SecurityAccounts.class)
                .block();
    }


    public String getAssetsFromAccount(String accessToken, String accountId) {
        WebClient client = webClientBuilder
                .baseUrl("https://api-sandbox.commerzbank.com/securities-api/v3")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return client.get()
                .uri("/security-accounts/" + accountId + "/assets")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}

