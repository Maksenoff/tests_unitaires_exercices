package com.example.mediacity.repository;

import com.example.mediacity.model.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> store = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public Reservation save(Long memberId, Long bookId, LocalDate date) {
        Reservation reservation = new Reservation(sequence.getAndIncrement(), memberId, bookId, date);
        store.add(reservation);
        return reservation;
    }

    @Override
    public List<Reservation> findByBookId(Long bookId) {
        return store.stream()
                .filter(r -> r.bookId().equals(bookId))
                .toList();
    }

    @Override
    public Optional<Reservation> findFirstByBookId(Long bookId) {
        return store.stream()
                .filter(r -> r.bookId().equals(bookId))
                .findFirst();
    }

    @Override
    public void deleteAll() {
        store.clear();
        sequence.set(1);
    }
}
