package com.example.roomapi.repository;

import com.example.roomapi.model.Reservation;
import com.example.roomapi.model.ReservationStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final AtomicLong sequence = new AtomicLong(0);
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();

    @Override
    public Reservation save(Long roomId, String personName, LocalDateTime startTime, LocalDateTime endTime, ReservationStatus status) {
        Long id = sequence.incrementAndGet();
        Reservation reservation = new Reservation(id, roomId, personName, startTime, endTime, status);
        reservations.put(id, reservation);
        return reservation;
    }

    @Override
    public Reservation update(Reservation reservation) {
        reservations.put(reservation.id(), reservation);
        return reservation;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public List<Reservation> findByRoomId(Long roomId) {
        return reservations.values().stream()
                .filter(r -> r.roomId().equals(roomId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        reservations.clear();
        sequence.set(0);
    }
}
