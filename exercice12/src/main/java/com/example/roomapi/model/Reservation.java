package com.example.roomapi.model;

import java.time.LocalDateTime;

public record Reservation(Long id, Long roomId, String personName, LocalDateTime startTime, LocalDateTime endTime, ReservationStatus status) {

    public Reservation withStatus(ReservationStatus newStatus) {
        return new Reservation(id, roomId, personName, startTime, endTime, newStatus);
    }
}
