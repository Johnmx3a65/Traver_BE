package com.parovsky.traver.Utils;

public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class");
    }
    public static int generateVerificationCode() {
        return (int) (Math.random() * (9999 - 1000) + 1000);
    }
}
