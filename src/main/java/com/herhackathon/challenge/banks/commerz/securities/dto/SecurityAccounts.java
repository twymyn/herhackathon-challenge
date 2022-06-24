package com.herhackathon.challenge.banks.commerz.securities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SecurityAccounts {
    @JsonProperty("securityAccountIDs")
    private List<AccountId> securityAccountIDs;
}
