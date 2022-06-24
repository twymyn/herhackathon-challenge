package com.herhackathon.challenge.banks.commerz.securities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
class AuthorizationCode {
    private String code;
    private String sessionState;
}
