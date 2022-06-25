package com.herhackathon.challenge.banks.commerz;

import com.herhackathon.challenge.SecurityConfiguration;
import com.herhackathon.challenge.banks.Bank;
import com.herhackathon.challenge.banks.commerz.oauth.CommerzOAuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;

/*
Configuration to be injected into webclient
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class CommerzWebClientConfiguration {

    private final CommerzOAuthProperties commerzOAuthProperties;

    private final SecurityConfiguration securityConfiguration;

    @Bean
    public ReactiveClientRegistrationRepository getRegistration() {
        ClientRegistration registration = ClientRegistration
                .withRegistrationId(Bank.COMMERZ.name())
                .tokenUri(commerzOAuthProperties.getTokenUrl())
                .clientId(commerzOAuthProperties.getClientId())
                .clientSecret(commerzOAuthProperties.getClientSecret())
                .authorizationGrantType(CLIENT_CREDENTIALS)
                .build();

        return new InMemoryReactiveClientRegistrationRepository(registration);
    }

    @Bean
    @Qualifier("commerzWebClientBuilder")
    public WebClient.Builder commerzWebClientBuilder(ReactiveClientRegistrationRepository clientRegistrations) {
        log.info("Initializing a WebClient.Builder for Commerzbank");
        InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);

        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);

        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId(Bank.COMMERZ.name());

        WebClient.Builder builder = securityConfiguration.getBasicWebClientBuilder();
        return builder.filter(oauth);
    }
}
