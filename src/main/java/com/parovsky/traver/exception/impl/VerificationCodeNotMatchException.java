package com.parovsky.traver.exception.impl;

public class VerificationCodeNotMatchException extends Exception {
    @Override
    public String getMessage() {
        return "Verification code not match";
    }
}
