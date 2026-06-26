package com.example.mediacity.model;

import java.time.LocalDate;

public record Reservation(Long id, Long memberId, Long bookId, LocalDate reservationDate) {}
