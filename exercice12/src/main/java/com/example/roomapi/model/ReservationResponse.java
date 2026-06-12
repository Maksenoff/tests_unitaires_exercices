package com.example.roomapi.model;

import java.time.LocalDateTime;

public record ReservationResponse(Long id, Long roomId, String personName, LocalDateTime startTime, LocalDateTime endTime, ReservationStatus status) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.roomId(),
                reservation.personName(),
                reservation.startTime(),
                reservation.endTime(),
                reservation.status()
        );
    }
}
