package com.example.roomapi.repository;

import com.example.roomapi.model.Reservation;
import com.example.roomapi.model.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Long roomId, String personName, LocalDateTime startTime, LocalDateTime endTime, ReservationStatus status);

    Reservation update(Reservation reservation);

    Optional<Reservation> findById(Long id);

    List<Reservation> findByRoomId(Long roomId);

    void deleteAll();
}
