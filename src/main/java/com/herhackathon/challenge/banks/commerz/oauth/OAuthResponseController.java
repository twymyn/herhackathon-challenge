package com.herhackathon.challenge.banks.commerz.oauth;

import com.herhackathon.challenge.banks.Bank;
import com.herhackathon.challenge.banks.BankProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OAuthResponseController {

    private final OAuthWebClient oAuthWebClient;

    private final BankProperties bankProperties;

    @GetMapping("/login/oauth2/callback/commerz")
    @ResponseBody
    public AuthorizationCode callbackEndpoint(
            @RequestParam(required = false) String caller,
            @RequestParam("code") String code,
            @RequestParam("session_state") String sessionState
    ) {
        AuthorizationCode authorizationCode = new AuthorizationCode(code, sessionState);
        log.info("original caller {}", caller);

        OAuthResponse oAuthResponse = oAuthWebClient.requestAccessTokenWithAuthCode(authorizationCode.getCode(), bankProperties.getRedirectUri(Bank.COMMERZ));
        log.info("Received access token: {}", oAuthResponse);

        oAuthWebClient.setOAuthResponse(oAuthResponse);

        return authorizationCode;
    }

    // TODO could be handled better with expiry date or refresh token
    @Scheduled(cron = "* */5 * * * *")
    public void resetStoredAccessToken() {
        log.info("clearing the stored access token");
        oAuthWebClient.setOAuthResponse(null);
    }
}
