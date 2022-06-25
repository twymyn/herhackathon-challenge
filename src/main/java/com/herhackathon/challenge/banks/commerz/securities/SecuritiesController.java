package com.herhackathon.challenge.banks.commerz.securities;

import com.herhackathon.challenge.banks.commerz.oauth.OAuthWebClient;
import com.herhackathon.challenge.banks.commerz.securities.dto.Assets;
import com.herhackathon.challenge.banks.commerz.securities.dto.Position;
import com.herhackathon.challenge.banks.commerz.securities.dto.SecurityAccounts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SecuritiesController {

    private final SecuritiesWebClient securitiesWebClient;

    private final OAuthWebClient oAuthWebClient;

    @GetMapping("/securities")
    @ResponseBody
    public ResponseEntity getAllSecurities() {
        try {
            String accessToken = oAuthWebClient.getAccessToken();
            SecurityAccounts allAccounts = securitiesWebClient.getAllAccounts(accessToken);
            return ResponseEntity.ok(allAccounts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/assets")
    @ResponseBody
    public ResponseEntity getAllAssets() {
        try {
            SecurityAccounts allAccounts = securitiesWebClient.getAllAccounts(oAuthWebClient.getAccessToken());

            List<Assets> allAssets = allAccounts.getSecurityAccountIDs().stream()
                    .map(accountId -> securitiesWebClient.getAssetsFromAccount(accountId.getSecurityAccountId()))
                    .toList();

            return ResponseEntity.ok(allAssets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/assets/positions")
    @ResponseBody
    public ResponseEntity getAllPositions() {
        try {
            SecurityAccounts allAccounts = securitiesWebClient.getAllAccounts(oAuthWebClient.getAccessToken());

            List<Position> allPositions = allAccounts.getSecurityAccountIDs().stream()
                    .map(accountId -> securitiesWebClient.getAssetsFromAccount(accountId.getSecurityAccountId()))
                    .map(Assets::getPositions)
                    .flatMap(Collection::stream)
                    .toList();

            return ResponseEntity.ok(allPositions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
