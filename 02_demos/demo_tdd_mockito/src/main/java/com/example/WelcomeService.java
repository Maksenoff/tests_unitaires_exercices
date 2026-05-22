package com.example;

public class WelcomeService {
    private final MessageSender messageSender;

    public WelcomeService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public boolean sendWelcomeMessage(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        String message = "Welcome " + email;

        return messageSender.send(email, message);
    }
}
