package com.herhackathon.challenge.banks.commerz.securities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Position {
    @JsonProperty("positionId")
    public String positionId;
    @JsonProperty("securityMasterdata")
    public SecurityMasterdata securityMasterdata;
    @JsonProperty("quantity")
    public Quantity quantity;
    @JsonProperty("currentPrice")
    public CurrentPrice currentPrice;
    @JsonProperty("initialPrice")
    public InitialPrice initialPrice;
    @JsonProperty("lastTradeDate")
    public String lastTradeDate;
    @JsonProperty("payout")
    public Payout payout;
    @JsonProperty("payedAccruedInterest")
    public PayedAccruedInterest payedAccruedInterest;
    @JsonProperty("AccruedInterest")
    public AccruedInterest accruedInterest;


    @Data
    class SecurityMasterdata {
        @JsonProperty("WKN")
        public String wkn;
        @JsonProperty("ISIN")
        public String isin;
        @JsonProperty("name")
        public String name;
        @JsonProperty("notationType")
        public String notationType;
        @JsonProperty("maturityDate")
        public String maturityDate;
        @JsonProperty("currency")
        public String currency;
        @JsonProperty("vote")
        public String vote;
    }

    @Data
    class Quantity {
        @JsonProperty("amount")
        public Integer amount;
        @JsonProperty("unit")
        public String unit;
    }

    @Data
    class CurrentPrice {
        @JsonProperty("amount")
        public Double amount;
        @JsonProperty("unit")
        public String unit;
        @JsonProperty("quoteDate")
        public String quoteDate;
        @JsonProperty("exchangeRate")
        public Double exchangeRate;
        @JsonProperty("exchangeRateDate")
        public String exchangeRateDate;
    }

    @Data
    class InitialPrice {

        @JsonProperty("amount")
        public Double amount;
        @JsonProperty("unit")
        public String unit;
        @JsonProperty("initialExchangeRate")
        public Double initialExchangeRate;
    }

    @Data
    class Payout {

        @JsonProperty("amount")
        public Integer amount;
        @JsonProperty("currency")
        public String currency;
    }

    @Data
    class PayedAccruedInterest {

        @JsonProperty("amount")
        public Integer amount;
        @JsonProperty("currency")
        public String currency;
    }

    @Data
    class AccruedInterest {

        @JsonProperty("amount")
        public Integer amount;
        @JsonProperty("currency")
        public String currency;
    }

}
