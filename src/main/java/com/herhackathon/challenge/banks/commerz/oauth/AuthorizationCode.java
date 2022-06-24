package com.herhackathon.challenge.banks.commerz.oauth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class AuthorizationCode {
    private String code;
    private String sessionState;
}
