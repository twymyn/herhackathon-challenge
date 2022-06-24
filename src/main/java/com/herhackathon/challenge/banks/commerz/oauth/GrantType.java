package com.herhackathon.challenge.banks.commerz.oauth;

public enum GrantType {
    AUTHORIZATION_CODE("authorization_code"),
    REFRESH_TOKEN("refresh_token"),
    PASSWORD("password"),
    CLIENT_CREDENTIALS("client_credentials");

    private final String parameterValue;

    GrantType(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getParameterValue() {
        return parameterValue;
    }
}

