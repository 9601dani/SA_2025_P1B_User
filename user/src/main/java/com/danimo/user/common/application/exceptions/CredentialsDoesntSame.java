package com.danimo.user.common.application.exceptions;

public class CredentialsDoesntSame extends RuntimeException {
    public CredentialsDoesntSame(String message) {
        super(message);
    }
}
