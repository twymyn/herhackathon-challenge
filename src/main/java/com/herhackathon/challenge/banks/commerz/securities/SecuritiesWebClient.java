package com.herhackathon.challenge.banks.commerz.securities;

import com.herhackathon.challenge.banks.commerz.CommerzApiProperties;
import com.herhackathon.challenge.banks.commerz.securities.dto.Assets;
import com.herhackathon.challenge.banks.commerz.securities.dto.SecurityAccounts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SecuritiesWebClient {

    private final WebClient.Builder webClientBuilder;

    private final CommerzApiProperties commerzApiProperties;

    public SecuritiesWebClient(WebClient.Builder webClientBuilder, CommerzApiProperties commerzApiProperties) {
        this.webClientBuilder = webClientBuilder;
        this.commerzApiProperties = commerzApiProperties;
    }

    SecurityAccounts getAllAccounts() {
        WebClient client = webClientBuilder
                .baseUrl(commerzApiProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return client.get()
                .uri(commerzApiProperties.getSecurityAccountsApi())
                .retrieve()
                .bodyToMono(SecurityAccounts.class)
                .block();
    }

    Assets getAssetsFromAccount(String accountId) {
        WebClient client = webClientBuilder
                .baseUrl(commerzApiProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return client.get()
                .uri(commerzApiProperties.getSecurityAccountsApi() + "/" + accountId + commerzApiProperties.getAssetsApi())
                .retrieve()
                .bodyToMono(Assets.class)
                .block();
    }

    public SecurityAccounts getAllAccounts(String accessToken) {
        WebClient client = webClientBuilder
                .baseUrl(commerzApiProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return client.get()
                .uri(commerzApiProperties.getSecurityAccountsApi())
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(SecurityAccounts.class)
                .block();
    }

    public Assets getAssetsFromAccount(String accessToken, String accountId) {
        WebClient client = webClientBuilder
                .baseUrl(commerzApiProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return client.get()
                .uri(commerzApiProperties.getSecurityAccountsApi() + "/" + accountId + commerzApiProperties.getAssetsApi())
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(Assets.class)
                .block();
    }

}

