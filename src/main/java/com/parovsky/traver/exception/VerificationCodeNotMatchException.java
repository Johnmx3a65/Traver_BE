package com.parovsky.traver.exception;

public class VerificationCodeNotMatchException extends Exception {
    @Override
    public String getMessage() {
        return "Verification code not match";
    }
}
