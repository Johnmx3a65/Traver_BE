package com.parovsky.traver.utils;

import java.util.Random;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }
    public static int generateVerificationCode() {
        return (int) (Math.random() * (9999 - 1000) + 1000);
    }

    public static String generateRandomString(int length) {
        Random random = new Random();
        String pattern = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            char c = pattern.charAt(random.nextInt(pattern.length()));
            sb.append(c);
        }

        return sb.toString();
    }
}
