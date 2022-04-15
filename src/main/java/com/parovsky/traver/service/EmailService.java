package com.parovsky.traver.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
