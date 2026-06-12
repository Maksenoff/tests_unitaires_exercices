package com.example;

public record BookingReceipt(
        String roomCode,
        String userEmail,
        String message
) {
}
