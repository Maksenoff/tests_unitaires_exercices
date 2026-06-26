package com.example.mediacity.repository;

import com.example.mediacity.model.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Long memberId, Long bookId, LocalDate date);
    List<Reservation> findByBookId(Long bookId);
    Optional<Reservation> findFirstByBookId(Long bookId);
    void deleteAll();
}
