package com.herhackathon.challenge.banks.commerz.securities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Assets {
    @JsonProperty("securitiesAccountId")
    private String securitiesAccountId;

    @JsonProperty("creationDay")
    private String creationDay;

    @JsonProperty("effectiveDay")
    private String effectiveDay;

    @JsonProperty("positions")
    private List<Position> positions;
}
