package com.herhackathon.challenge.banks;

import com.herhackathon.challenge.banks.commerz.securities.SecuritiesWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BankController {

    private final SecuritiesWebClient securitiesWebClient;
    private final BankProperties bankProperties;

    @GetMapping("/banks")
    public ResponseEntity<List<String>> getAllSupportedBanks() {
        try {
            List<String> banks = Arrays.stream(Bank.values()).map(bank -> bank.fullName).collect(Collectors.toList());
            return ResponseEntity.ok(banks);
        } catch (Exception e) {
            log.info("error", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/banks/{bank}/login")
    public ResponseEntity<String> getBankLoginUri(@PathVariable("bank") String bankName) {
        try {
            Bank bank = Bank.findByName(bankName);
            URL loginUrl = bankProperties.getLoginUrl(bank);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(loginUrl.toURI())
                    .build();
        } catch (Exception e) {
            log.warn("error", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
