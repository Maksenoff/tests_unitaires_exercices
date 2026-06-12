package com.example;

import java.time.LocalDateTime;

public record Booking(
        String userEmail,
        String roomCode,
        int attendees,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {
}
