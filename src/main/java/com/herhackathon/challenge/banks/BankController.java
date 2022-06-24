package com.herhackathon.challenge.banks;

import com.herhackathon.challenge.banks.commerz.securities.SecuritiesWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BankController {

    private final SecuritiesWebClient securitiesWebClient;
    private final BankProperties bankProperties;

    @GetMapping("/banks")
    public ResponseEntity getAllSupportedBanks() {
        try {
            return ResponseEntity.ok(Bank.values());
        } catch (Exception e) {
            log.info("error", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/banks/{bank}/login")
    public ResponseEntity getBankLoginUri(@PathVariable("bank") Bank bank) {
        try {
            URL loginUrl = bankProperties.getLoginUrl(bank);
            return ResponseEntity.ok(loginUrl);
        } catch (Exception e) {
            log.info("error", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
