package com.herhackathon.challenge.banks.commerz.securities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SecuritiesController {

    private final SecuritiesWebClient securitiesWebClient;

    @GetMapping("/securities")
    @ResponseBody
    public ResponseEntity<SecurityAccounts> getAllSecurities() {
        SecurityAccounts allAccounts = securitiesWebClient.getAllAccounts();
        return ResponseEntity.ok(allAccounts);
    }

    @GetMapping("/assets")
    @ResponseBody
    public ResponseEntity<List<String>> getAllAssets() {
        SecurityAccounts allAccounts = securitiesWebClient.getAllAccounts();

        List<String> allAssets = allAccounts.getSecurityAccountIDs().stream().map(accountId -> securitiesWebClient.getAssetsFromAccount(accountId.getSecurityAccountId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(allAssets);
    }
}
