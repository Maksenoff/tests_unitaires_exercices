package com.example;

public interface NotificationGateway {

    void sendConfirmation(String userEmail, Booking booking);
}
