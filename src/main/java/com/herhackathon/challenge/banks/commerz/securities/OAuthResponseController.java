package com.herhackathon.challenge.banks.commerz.securities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class OAuthResponseController {

    private final BlockingQueue<AuthorizationCode> responses = new SynchronousQueue<>();

    @GetMapping("/login/oauth2/code/commerz")
    @ResponseBody
    public AuthorizationCode callbackEndpoint(
            @RequestParam("caller") String caller,
            @RequestParam("code") String code,
            @RequestParam("session_state") String sessionState
    ) {
        AuthorizationCode authorizationCode = new AuthorizationCode(code, sessionState);
        log.info("original caller {}", caller);
        responses.add(authorizationCode);
        return authorizationCode;
    }

    /**
     * Blocking call to get the response from the login redirect.
     *
     * @param timeout how long to wait before giving up
     * @param timeUnit unit a {@code TimeUnit} determining how to interpret the {@code timeout} parameter
     * @return code and session state from the OAuth login process.
     */
    public AuthorizationCode getResponse(long timeout, TimeUnit timeUnit) {
        try {
            return responses.poll(timeout, timeUnit);
        } catch (InterruptedException e) {
            log.info("Timeout. Got no callback call from OAuth login");
        }
        return null;
    }
}
