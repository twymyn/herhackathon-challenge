package com.herhackathon.challenge.banks.commerz;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class CommerzApiProperties {

    @Value("${commerz.api.base-url}")
    private String baseUrl;

    @Value("${commerz.api.path.security-accounts}")
    private String securityAccountsApi;

    @Value("${commerz.api.path.assets}")
    private String assetsApi;

}
