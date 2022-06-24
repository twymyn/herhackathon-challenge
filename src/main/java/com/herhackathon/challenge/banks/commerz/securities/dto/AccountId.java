package com.herhackathon.challenge.banks.commerz.securities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountId {

    @JsonProperty("securityAccountId")
    private String securityAccountId;

    @JsonProperty("pseudonymizedAccountId")
    private String pseudonymizedAccountId;
}
